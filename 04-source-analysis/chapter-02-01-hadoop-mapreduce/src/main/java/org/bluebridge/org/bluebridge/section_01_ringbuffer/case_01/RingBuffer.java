package org.bluebridge.org.bluebridge.section_01_ringbuffer.case_01;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 简化版环形缓冲区（对齐 Hadoop 3.5.0 MapTask.MapOutputBuffer 核心设计）
 *
 * 与 Hadoop 源码对齐的关键点：
 * 1. kvbuffer（byte[]）+ kvmeta（IntBuffer）共享同一块内存，kvmeta 是 kvbuffer 的 int 视图
 * 2. 元数据字段顺序与 Hadoop 完全一致：VALSTART(0), KEYSTART(1), PARTITION(2), VALLEN(3)
 * 3. 数据区从左向右增长（bufindex），索引区从右向左增长（kvindex）
 * 4. 指针命名对齐：bufindex/bufstart/bufend（数据区），kvindex/kvstart/kvend（索引区）
 * 5. 溢写阈值 softLimit = capacity * spillPercent（默认 0.8）
 * 6. 溢写时排序规则：先按 partition 升序，再按 key 字节升序
 *
 * 简化的非核心部分（便于教学）：
 * - 序列化：直接用 String.getBytes()，不引入 Writable/Serializer
 * - 溢写输出：打印到控制台，不写磁盘文件
 * - 并发：去掉 SpillThread 异步溢写，改为同步溢写
 * - Combiner：不执行局部合并
 *
 * @author lingwh
 * @date 2026/7/28 17:05
 */
public class RingBuffer {

    // ==================== 元数据字段索引（与 Hadoop 完全一致） ====================

    /** value 在 kvbuffer 中的起始偏移 */
    private static final int VALSTART = 0;
    /** key 在 kvbuffer 中的起始偏移 */
    private static final int KEYSTART = 1;
    /** 分区号 */
    private static final int PARTITION = 2;
    /** value 的长度 */
    private static final int VALLEN = 3;
    /** 每条元数据的 int 个数 */
    private static final int NMETA = 4;
    /** 每条元数据占用的字节数 */
    private static final int METASIZE = NMETA * 4;  // = 16

    // ==================== 缓冲区核心字段（与 Hadoop 对齐命名） ====================

    /** 底层字节数组，存储序列化后的 key/value 数据 */
    private final byte[] kvbuffer;

    /** 元数据视图，覆盖在 kvbuffer 同一块内存上（Hadoop 精髓） */
    private final IntBuffer kvmeta;

    /** 缓冲区总容量（字节） */
    private final int capacity;

    /** 数据区写入指针（从左向右增长），对应 Hadoop 的 bufindex */
    private int bufindex = 0;

    /** 索引区写入指针（int 为单位，从右向左增长），对应 Hadoop 的 kvindex */
    private int kvindex;

    /** 溢写时数据区的起始位置，对应 Hadoop 的 bufstart */
    private int bufstart = 0;

    /** 溢写时索引区的起始位置（int 为单位），对应 Hadoop 的 kvstart */
    private int kvstart;

    /** 当前缓冲区中记录条数 */
    private int recordCount = 0;

    /** 溢写阈值（字节数），对应 Hadoop 的 softLimit */
    private final int softLimit;

    // ==================== 构造方法 ====================

    /**
     * 构造指定容量的环形缓冲区，溢写阈值默认 80%
     * @param capacity 缓冲区容量（字节）
     */
    public RingBuffer(int capacity) {
        this(capacity, 0.8f);
    }

    /**
     * 构造指定容量和溢写阈值的环形缓冲区
     *
     * 初始化过程与 Hadoop 一致：
     * 1. 分配 byte[] kvbuffer
     * 2. 用 ByteBuffer.wrap(kvbuffer) 包装后 asIntBuffer() 得到 kvmeta 视图
     * 3. kvindex 指向缓冲区末尾（索引区从右向左增长）
     *
     * @param capacity      缓冲区容量（字节）
     * @param spillPercent  溢写阈值（0 ~ 1）
     */
    public RingBuffer(int capacity, float spillPercent) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("缓冲区容量必须大于 0");
        }
        // 与 Hadoop 一致：容量按 METASIZE 对齐
        capacity -= capacity % METASIZE;
        this.capacity = capacity;
        this.kvbuffer = new byte[capacity];

        // 与 Hadoop 一致：kvmeta 是 kvbuffer 的 IntBuffer 视图，使用本地字节序
        this.kvmeta = ByteBuffer.wrap(kvbuffer)
                .order(ByteOrder.nativeOrder())
                .asIntBuffer();

        // kvindex 指向最后一个 int 槽位（索引区从右向左增长）
        this.kvindex = kvmeta.capacity() - NMETA;
        this.kvstart = kvindex;

        // softLimit = capacity * spillPercent
        this.softLimit = (int) (capacity * spillPercent);
    }

    // ==================== 核心方法：collect（对应 Hadoop 的 collect） ====================

    /**
     * 向缓冲区写入一条记录（对应 Hadoop MapOutputBuffer.collect）
     *
     * 写入流程：
     * 1. 将 key、value 序列化后写入数据区（bufindex 向右移动）
     * 2. 将元数据（VALSTART, KEYSTART, PARTITION, VALLEN）写入索引区（kvindex 向左移动）
     * 3. 检查是否超过 softLimit，若超过则触发溢写
     *
     * @param partition 分区号
     * @param key       键
     * @param value     值
     * @return true 表示本次写入触发了溢写
     */
    public synchronized boolean collect(int partition, String key, String value) {
        byte[] keyBytes = key.getBytes();
        byte[] valueBytes = value.getBytes();

        int keyLen = keyBytes.length;
        int valLen = valueBytes.length;
        int dataSize = keyLen + valLen;

        if (dataSize + METASIZE > capacity) {
            throw new IllegalArgumentException("单条记录大小超过缓冲区容量");
        }

        // ---- 1. 数据区写入 key + value（bufindex 向右增长）----
        int keystart = bufindex;
        System.arraycopy(keyBytes, 0, kvbuffer, bufindex, keyLen);
        bufindex += keyLen;

        int valstart = bufindex;
        System.arraycopy(valueBytes, 0, kvbuffer, bufindex, valLen);
        bufindex += valLen;

        // ---- 2. 索引区写入元数据（kvindex 向左增长，与 Hadoop 一致的字段顺序）----
        kvmeta.put(kvindex + VALSTART, valstart);
        kvmeta.put(kvindex + KEYSTART, keystart);
        kvmeta.put(kvindex + PARTITION, partition);
        kvmeta.put(kvindex + VALLEN, valLen);
        kvindex -= NMETA;

        recordCount++;

        // ---- 3. 检查是否需要溢写 ----
        if (getUsedSize() >= softLimit) {
            spill();
            return true;
        }
        return false;
    }

    // ==================== 溢写（对应 Hadoop 的 sortAndSpill） ====================

    /**
     * 溢写缓冲区中的数据（对应 Hadoop 的 sortAndSpill）
     *
     * 流程：
     * 1. 读取所有记录的元数据
     * 2. 按 partition 升序 -> key 字节升序 排序
     * 3. 顺序输出（Hadoop 中写出为磁盘文件，这里简化为控制台输出）
     * 4. 重置缓冲区指针
     */
    private void spill() {
        System.out.println("【溢写】记录数：" + recordCount
                + "，数据区已用：" + (bufindex - bufstart)
                + " 字节，索引区已用：" + (kvstart - kvindex) / NMETA * METASIZE
                + " 字节");

        // 1. 读取所有元数据
        List<MetaEntry> metaList = new ArrayList<>();
        int pos = kvindex + NMETA;  // 从最旧的一条开始
        for (int i = 0; i < recordCount; i++) {
            int valstart  = kvmeta.get(pos + VALSTART);
            int keystart  = kvmeta.get(pos + KEYSTART);
            int partition = kvmeta.get(pos + PARTITION);
            int vallen    = kvmeta.get(pos + VALLEN);
            metaList.add(new MetaEntry(partition, keystart, valstart, vallen));
            pos += NMETA;
        }

        // 2. 排序：先按 partition，再按 key 字节（与 Hadoop 一致）
        metaList.sort((a, b) -> {
            if (a.partition != b.partition) {
                return Integer.compare(a.partition, b.partition);
            }
            return compareKeyBytes(a, b);
        });

        // 3. 顺序输出（模拟溢写文件）
        for (MetaEntry meta : metaList) {
            int keyLen = meta.valstart - meta.keystart;
            String key = new String(kvbuffer, meta.keystart, keyLen);
            String value = new String(kvbuffer, meta.valstart, meta.vallen);
            System.out.println("  分区 " + meta.partition + " -> " + key + " : " + value);
        }

        // 4. 重置缓冲区
        bufstart = bufindex = 0;
        kvindex = kvmeta.capacity() - NMETA;
        kvstart = kvindex;
        recordCount = 0;
    }

    /**
     * 比较 two 条记录的 key 字节（无符号逐字节比较，与 Hadoop 的 RawComparator 对齐）
     */
    private int compareKeyBytes(MetaEntry a, MetaEntry b) {
        int lenA = a.valstart - a.keystart;
        int lenB = b.valstart - b.keystart;
        int minLen = Math.min(lenA, lenB);
        for (int i = 0; i < minLen; i++) {
            int cmp = (kvbuffer[a.keystart + i] & 0xFF) - (kvbuffer[b.keystart + i] & 0xFF);
            if (cmp != 0) {
                return cmp;
            }
        }
        return Integer.compare(lenA, lenB);
    }

    // ==================== 辅助方法 ====================

    /**
     * 手动触发最终溢写（对应 Hadoop 的 flush）
     */
    public synchronized void flush() {
        if (recordCount > 0) {
            spill();
        }
    }

    /**
     * 获取已用空间大小（字节）
     * 数据区已用 = bufindex - bufstart
     * 索引区已用 = (kvstart - kvindex) / NMETA * METASIZE
     */
    public int getUsedSize() {
        int dataUsed = bufindex - bufstart;
        int indexUsed = (kvstart - kvindex) / NMETA * METASIZE;
        return dataUsed + indexUsed;
    }

    /**
     * 获取当前记录数
     */
    public int getRecordCount() {
        return recordCount;
    }

    // ==================== 元数据实体（对应 Hadoop kvmeta 中的一条记录） ====================

    /**
     * 元数据条目，对应 kvmeta 中的一条记录
     */
    private static class MetaEntry {
        final int partition;
        final int keystart;
        final int valstart;
        final int vallen;

        MetaEntry(int partition, int keystart, int valstart, int vallen) {
            this.partition = partition;
            this.keystart = keystart;
            this.valstart = valstart;
            this.vallen = vallen;
        }
    }
}
