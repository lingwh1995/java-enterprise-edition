# MapReduce 全流程数据变化（以 WordCount 为例）

> 示例设置：`numReduceTasks = 2`，展示两个分区的完整数据流转过程

## 输入文件

**input_words.txt**
```
hadoop ubuntu suse centos linux       ← 行1
macos freebsd windows arch redhat     ← 行2
ubuntu centos linux freebsd windows   ← 行3
arch redhat hadoop suse macos         ← 行4
hadoop freebsd windows centos redhat  ← 行5
```

---

## 阶段 1 - Read阶段：InputFormat + RecordReader

**TextInputFormat + LineRecordReader 按行读取，产生 (字节偏移量, 行内容) 对**

| Key (LongWritable) | Value (Text) |
|---|---|
| 0 | hadoop ubuntu suse centos linux |
| 36 | macos freebsd windows arch redhat |
| 72 | ubuntu centos linux freebsd windows |
| 108 | arch redhat hadoop suse macos |
| 144 | hadoop freebsd windows centos redhat |

---

## 阶段 2 - Map阶段：Map（用户 map 方法）
**每行按空格切分，每个单词输出 (word, 1)**
```
行1 → (hadoop,1) (ubuntu,1) (suse,1) (centos,1) (linux,1)
行2 → (macos,1) (freebsd,1) (windows,1) (arch,1) (redhat,1)
行3 → (ubuntu,1) (centos,1) (linux,1) (freebsd,1) (windows,1)
行4 → (arch,1) (redhat,1) (hadoop,1) (suse,1) (macos,1)
行5 → (hadoop,1) (freebsd,1) (windows,1) (centos,1) (redhat,1)
```

共 25 个 KV 对，全部输出到 `context.write()` → 进入 `MapOutputBuffer.collect()`。

---

## 阶段 3 - Partition阶段：Partitioner 分区

**Map 每输出一条 KV 对，Partitioner 决定它被发往哪个 Reduce 任务**

### 分区公式（HashPartitioner）

```
partition = (key.hashCode() & Integer.MAX_VALUE) % numReduceTasks
```

`& Integer.MAX_VALUE` 确保结果为正数，再对 Reduce 任务数取模得到分区号。

### 各单词的实际分区计算（numReduceTasks = 2）

```
key       hashCode()    & MAX_VALUE    % 2   → partition
hadoop    -1224864731   922618917      1     → 1
ubuntu     -851256601   1296227047     1     → 1
suse          3542068   3542068        0     → 0
centos    -1364013684   783469964      0     → 0
linux       102977780   102977780      0     → 0
macos       103652211   103652211      1     → 1
freebsd    -603799481   1543684167     1     → 1
windows    1349493379   1349493379     1     → 1
arch          3002454   3002454        0     → 0
redhat     -934886294   1212597354     0     → 0
```

### 分区结果

```
Partition 0 ← suse, centos, linux, arch, redhat（5 个 key，12 条记录）
Partition 1 ← hadoop, ubuntu, macos, freebsd, windows（5 个 key，13 条记录）
```

分区号写入环形缓冲区的索引区（meta 中的 PARTITION 字段），后续 Spill 和 Shuffle 都按分区号处理数据。

---

## 阶段 4 - Collect阶段：写入环形缓冲区

**每条 (word, 1) 被 Partitioner 分配分区号后，序列化写入环形缓冲区**

### 缓冲区布局

```
 equator                                                    bufvoid
   |                                                          |
   v                                                          v
 +--------+--------+--------+--------+--------+           +------+------+------+------+
 | hadoop | 1      | ubuntu | 1      | suse   | ...       | meta | meta | meta | meta |
 | P1     |        | P1     |        | P0     |           | P1,0 | P1,0 | P0,0 | P0,0 |
 +--------+--------+--------+--------+--------+           +------+------+------+------+
   ^                                                          ^
 bufindex                                                  kvindex
 (向右增长)                                                 (向左增长)
```

- **数据区**（从左向右）：存储序列化后的 key/value 字节
- **索引区**（从右向左）：每条 16 字节，存储 `VALSTART, KEYSTART, PARTITION, VALLEN`

### 以第 1 条记录为例的 collect 过程

```
collect(partition=1, key="hadoop", value=1)    ← hadoop 的 partition = 1

数据区（bufindex 向右写）：
  keystart=0   → 写入 "hadoop" (6字节)
  valstart=6   → 写入 1 (序列化 IntWritable, 4字节)
  bufindex=10  → 指针右移

索引区（kvindex 向左写，16字节/条）：
  kvmeta.put(VALSTART,  6)   ← value 起始偏移
  kvmeta.put(KEYSTART,  0)   ← key 起始偏移
  kvmeta.put(PARTITION, 1)   ← 分区号（hadoop → partition 1）
  kvmeta.put(VALLEN,    4)   ← value 长度
  kvindex -= 4               ← 指针左移
```

### 全部 25 条记录写入后的缓冲区状态

```
数据区（从左到右，按 Map 输出顺序写入，含分区号）：
[hadoop P1][1][ubuntu P1][1][suse P0][1][centos P0][1][linux P0][1]
[macos P1][1][freebsd P1][1][windows P1][1][arch P0][1][redhat P0][1]
[ubuntu P1][1][centos P0][1][linux P0][1][freebsd P1][1][windows P1][1]
[arch P0][1][redhat P0][1][hadoop P1][1][suse P0][1][macos P1][1]
[hadoop P1][1][freebsd P1][1][windows P1][1][centos P0][1][redhat P0][1]

索引区（从右到左，每条 16 字节，含分区号）：
[kvindex] [meta25 P0] [meta24 P0] ... [meta2 P1] [meta1 P1] [kvstart]
  ← 左移                                                右移 →
```

---

## 阶段 5 - Spill（sortAndSpill）

当已用空间 ≥ softLimit（80%）时触发溢写。

### 5.1 记录溢写边界

```
bufend = bufindex   ← 数据区终点
kvend  = kvindex    ← 索引区终点
```

### 5.2 排序（先按 partition，再按 key 字节）

**排序前（写入顺序）：**
```
hadoop(P1), ubuntu(P1), suse(P0), centos(P0), linux(P0),
macos(P1), freebsd(P1), windows(P1), arch(P0), redhat(P0),
ubuntu(P1), centos(P0), linux(P0), freebsd(P1), windows(P1),
arch(P0), redhat(P0), hadoop(P1), suse(P0), macos(P1),
hadoop(P1), freebsd(P1), windows(P1), centos(P0), redhat(P0)
```

**排序后（先按 partition 升序，同 partition 内按 key 字节升序）：**
```
--- Partition 0（12条）---
arch, arch, centos, centos, centos, linux, linux, redhat, redhat, redhat, suse, suse

--- Partition 1（13条）---
freebsd, freebsd, freebsd, hadoop, hadoop, hadoop, macos, macos, ubuntu, ubuntu, windows, windows, windows
```

### 5.3 写出溢写文件（磁盘）

```
spill0.out（按 partition 分段，段内按 key 排序）：
  ┌── Partition 0 ──────────────────┐
  │  arch     1                     │
  │  arch     1                     │
  │  centos   1                     │
  │  centos   1                     │
  │  centos   1                     │
  │  linux    1                     │
  │  linux    1                     │
  │  redhat   1                     │
  │  redhat   1                     │
  │  redhat   1                     │
  │  suse     1                     │
  │  suse     1                     │
  ├── Partition 1 ──────────────────┤
  │  freebsd  1                     │
  │  freebsd  1                     │
  │  freebsd  1                     │
  │  hadoop   1                     │
  │  hadoop   1                     │
  │  hadoop   1                     │
  │  macos    1                     │
  │  macos    1                     │
  │  ubuntu   1                     │
  │  ubuntu   1                     │
  │  windows  1                     │
  │  windows  1                     │
  │  windows  1                     │
  └─────────────────────────────────┘
```

### 5.4 重置缓冲区

```
setEquator(0) → bufindex=0, kvindex=capacity-NMETA, 继续接收新数据
```

---

## 阶段 6 - Merge（Map 端合并）

Map 任务结束后，`flush()` 溢写剩余数据，然后将所有 spill 文件合并（merge）成一个最终文件：

```
map 输出文件（按 partition 分段，段内按 key 排序）：
  ┌── Partition 0（12条）──────────────────────────────────────┐
  │  arch 1, arch 1, centos 1, centos 1, centos 1,            │
  │  linux 1, linux 1,                                         │
  │  redhat 1, redhat 1, redhat 1,                             │
  │  suse 1, suse 1                                            │
  ├── Partition 1（13条）──────────────────────────────────────┤
  │  freebsd 1, freebsd 1, freebsd 1,                          │
  │  hadoop 1, hadoop 1, hadoop 1,                             │
  │  macos 1, macos 1,                                         │
  │  ubuntu 1, ubuntu 1,                                       │
  │  windows 1, windows 1, windows 1                           │
  └────────────────────────────────────────────────────────────┘
```

---

## 阶段 7 - Shuffle（Reduce 拉取数据）

每个 Reduce 任务通过 HTTP 从 Map 节点拉取自己负责的 partition 数据：

```
Reduce Task 0 ← 拉取所有 Map 输出的 Partition 0（12 条记录：arch, centos, linux, redhat, suse）
Reduce Task 1 ← 拉取所有 Map 输出的 Partition 1（13 条记录：freebsd, hadoop, macos, ubuntu, windows）
```

---

## 阶段 8 - Sort（Reduce 端合并排序）

Reduce 端将所有拉取到的数据进行归并排序，相同 key 的值分到同一组：

**Reduce Task 0（Partition 0）：**
```
arch     → [1, 1]
centos   → [1, 1, 1]
linux    → [1, 1]
redhat   → [1, 1, 1]
suse     → [1, 1]
```

**Reduce Task 1（Partition 1）：**
```
freebsd  → [1, 1, 1]
hadoop   → [1, 1, 1]
macos    → [1, 1]
ubuntu   → [1, 1]
windows  → [1, 1, 1]
```

---

## 阶段 9 - Reduce（用户 reduce 方法）

对每个 key 的 value 列表求和：

**Reduce Task 0（Partition 0）：**
```
reduce("arch",     [1,1])    → (arch, 2)
reduce("centos",   [1,1,1])  → (centos, 3)
reduce("linux",    [1,1])    → (linux, 2)
reduce("redhat",   [1,1,1])  → (redhat, 3)
reduce("suse",     [1,1])    → (suse, 2)
```

**Reduce Task 1（Partition 1）：**
```
reduce("freebsd",  [1,1,1])  → (freebsd, 3)
reduce("hadoop",   [1,1,1])  → (hadoop, 3)
reduce("macos",    [1,1])    → (macos, 2)
reduce("ubuntu",   [1,1])    → (ubuntu, 2)
reduce("windows",  [1,1,1])  → (windows, 3)
```

---

## 阶段 10 - OutputFormat（写出结果）

`TextOutputFormat` 将结果写入 HDFS / 本地文件，每个 Reduce Task 生成一个输出文件：

```
part-r-00000（Reduce Task 0 输出）：    part-r-00001（Reduce Task 1 输出）：
  arch     2                              freebsd  3
  centos   3                              hadoop   3
  linux    2                              macos    2
  redhat   3                              ubuntu   2
  suse     2                              windows  3
```

---

## 全流程数据变化总结

```
[输入文件]           [Map 输入]         [Map 输出]           [Partition]                [环形缓冲区]
 5 行文本     →      5 个 KV 对   →     25 个 KV 对   →      25 条分配分区号      →     数据区 + 索引区
 25 个单词            (偏移, 行)          (word, 1)            P0: 12条, P1: 13条          │
                                                                                              │ 80% 触发
                                                                                              ▼
[Reduce 输出]        [Reduce 输入]       [Spill 文件]                 [Spill]
 2 个输出文件  ←     2 组 Reduce    ←   P0 段 + P1 段          ←    按 partition → key 排序
 P0: 5 个 KV         P0: 5 组 KV         按 partition → key
 P1: 5 个 KV         P1: 5 组 KV
```

关键转折点有两个：
- **Partitioner**：决定数据由哪个 Reduce 处理，是并行化的基础
- **环形缓冲区**：Map 输出和磁盘溢写之间的桥梁，数据在这里从"写入顺序"变为"排序顺序"，是 MapReduce 高性能的核心设计
