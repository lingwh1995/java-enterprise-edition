# MapReduce 全流程数据变化（以 WordCount 为例）

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

## 阶段 3 - Collect阶段：写入环形缓冲区

**每条 (word, 1) 被序列化后写入环形缓冲区**

假设 `numReduceTasks=1`，`HashPartitioner` 将所有记录分到 partition 0。

### 缓冲区布局

```
 equator                                                    bufvoid
   |                                                          |
   v                                                          v
 +--------+--------+--------+--------+--------+           +------+------+------+------+
 | hadoop | 1      | ubuntu | 1      | suse   | ...       | meta | meta | meta | meta |
 +--------+--------+--------+--------+--------+           +------+------+------+------+
   ^                                                          ^
 bufindex                                                  kvindex
 (向右增长)                                                 (向左增长)
```

- **数据区**（从左向右）：存储序列化后的 key/value 字节
- **索引区**（从右向左）：每条 16 字节，存储 `VALSTART, KEYSTART, PARTITION, VALLEN`

### 以第 1 条记录为例的 collect 过程

```
collect(partition=0, key="hadoop", value=1)

数据区（bufindex 向右写）：
  keystart=0   → 写入 "hadoop" (6字节)
  valstart=6   → 写入 1 (序列化 IntWritable, 4字节)
  bufindex=10  → 指针右移

索引区（kvindex 向左写，16字节/条）：
  kvmeta.put(VALSTART,  6)   ← value 起始偏移
  kvmeta.put(KEYSTART,  0)   ← key 起始偏移
  kvmeta.put(PARTITION, 0)   ← 分区号
  kvmeta.put(VALLEN,    4)   ← value 长度
  kvindex -= 4               ← 指针左移
```

### 全部 25 条记录写入后的缓冲区状态

```
数据区（从左到右，写入顺序）：
[hadoop][1][ubuntu][1][suse][1][centos][1][linux][1]
[macos][1][freebsd][1][windows][1][arch][1][redhat][1]
[ubuntu][1][centos][1][linux][1][freebsd][1][windows][1]
[arch][1][redhat][1][hadoop][1][suse][1][macos][1]
[hadoop][1][freebsd][1][windows][1][centos][1][redhat][1]

索引区（从右到左，每条 16 字节）：
[kvindex] [meta25] [meta24] ... [meta2] [meta1] [kvstart]
  ← 左移                                              右移 →
```

---

## 阶段 4：Spill（sortAndSpill）

当已用空间 ≥ softLimit（80%）时触发溢写。

### 4.1 记录溢写边界

```
bufend = bufindex   ← 数据区终点
kvend  = kvindex    ← 索引区终点
```

### 4.2 排序（按 partition → key 字节）

**排序前（写入顺序）：**
```
hadoop, ubuntu, suse, centos, linux, macos, freebsd, windows, arch, redhat,
ubuntu, centos, linux, freebsd, windows, arch, redhat, hadoop, suse, macos,
hadoop, freebsd, windows, centos, redhat
```

**排序后（按 key 字节升序）：**
```
arch, arch, centos, centos, centos, freebsd, freebsd, freebsd,
hadoop, hadoop, hadoop, linux, linux, macos, macos, redhat, redhat, redhat,
suse, suse, ubuntu, ubuntu, windows, windows, windows
```

### 4.3 写出溢写文件（磁盘）

```
spill0.out:
  arch     1
  arch     1
  centos   1
  centos   1
  centos   1
  freebsd  1
  freebsd  1
  freebsd  1
  hadoop   1
  hadoop   1
  hadoop   1
  linux    1
  linux    1
  macos    1
  macos    1
  redhat   1
  redhat   1
  redhat   1
  suse     1
  suse     1
  ubuntu   1
  ubuntu   1
  windows  1
  windows  1
  windows  1
```

### 4.4 重置缓冲区

```
setEquator(0) → bufindex=0, kvindex=capacity-NMETA, 继续接收新数据
```

---

## 阶段 5：Merge（Map 端合并）

Map 任务结束后，`flush()` 溢写剩余数据，然后将所有 spill 文件合并（merge）成一个最终文件：

```
map 输出文件（按 partition 分段，段内按 key 排序）：
  Partition 0:
    arch 1, arch 1, centos 1, centos 1, centos 1,
    freebsd 1, freebsd 1, freebsd 1,
    hadoop 1, hadoop 1, hadoop 1,
    linux 1, linux 1,
    macos 1, macos 1,
    redhat 1, redhat 1, redhat 1,
    suse 1, suse 1,
    ubuntu 1, ubuntu 1,
    windows 1, windows 1, windows 1
```

---

## 阶段 6：Shuffle（Reduce 拉取数据）

Reduce 任务通过 HTTP 从 Map 节点拉取自己负责的 partition 数据：

```
Reduce Task 0 ← 拉取 Map 输出的 Partition 0（全部 25 条记录）
```

---

## 阶段 7：Sort（Reduce 端合并排序）

Reduce 端将所有拉取到的数据进行归并排序，相同 key 的值分到同一组：

```
arch     → [1, 1]
centos   → [1, 1, 1]
freebsd  → [1, 1, 1]
hadoop   → [1, 1, 1]
linux    → [1, 1]
macos    → [1, 1]
redhat   → [1, 1, 1]
suse     → [1, 1]
ubuntu   → [1, 1]
windows  → [1, 1, 1]
```

---

## 阶段 8：Reduce（用户 reduce 方法）

对每个 key 的 value 列表求和：

```
reduce("arch",     [1,1])    → (arch, 2)
reduce("centos",   [1,1,1])  → (centos, 3)
reduce("freebsd",  [1,1,1])  → (freebsd, 3)
reduce("hadoop",   [1,1,1])  → (hadoop, 3)
reduce("linux",    [1,1])    → (linux, 2)
reduce("macos",    [1,1])    → (macos, 2)
reduce("redhat",   [1,1,1])  → (redhat, 3)
reduce("suse",     [1,1])    → (suse, 2)
reduce("ubuntu",   [1,1])    → (ubuntu, 2)
reduce("windows",  [1,1,1])  → (windows, 3)
```

---

## 阶段 9：OutputFormat（写出结果）

`TextOutputFormat` 将结果写入 HDFS / 本地文件：

```
arch     2
centos   3
freebsd  3
hadoop   3
linux    2
macos    2
redhat   3
suse     2
ubuntu   2
windows  3
```

---

## 全流程数据变化总结

```
[输入文件]                [Map 输入]              [Map 输出]              [环形缓冲区]
 5 行文本          →      5 个 KV 对       →      25 个 KV 对      →     数据区 + 索引区
 25 个单词                (偏移, 行)               (word, 1)                │
                                                                            │ 80% 触发
                                                                            ▼
[Reduce 输出]             [Reduce 输入]           [Spill 文件]            [Spill]
 10 个 KV 对      ←      10 组 KV 对      ←      25 条排序         ←     按 key 字节排序
 (word, n)                (word, [1,1..])          按 key 字节排序
```

关键转折点就是**环形缓冲区**：它是 Map 输出和磁盘溢写之间的桥梁，数据在这里从"写入顺序"变为"排序顺序"，是 MapReduce 高性能的核心设计。
