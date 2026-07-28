package org.bluebridge.mapreduce.case_04_ringbuffer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 模拟 Hadoop MapOutputBuffer 的环形缓冲区（教学版）
 *
 * 与 Hadoop 一致的布局：
 * - 底层是一个固定大小的字节数组 buffer
 * - 数据区：从数组头部(0)向右增长，存放 key/value 序列化后的字节
 * - 索引区：从数组尾部(capacity)向左增长，每条索引 16 字节，存放：
 *     partition(4B) + keystart(4B) + valstart(4B) + vallength(4B)
 *   完全对齐 Hadoop 的 kvmeta 布局（PARTITION, KEYSTART, VALSTART, VALLEN）
 * - 当已用空间超过阈值(默认80%)时触发溢写
 * - 溢写时先对索引按分区号 → key 字节排序，再顺序写出（Hadoop 中写出到磁盘）
 *
 * @author lingwh
 * @date 2026/7/28 17:05
 */
public class RingBuffer {

    /** 底层字节数组 */
    private final byte[] buffer;

    /** 缓冲区总容量（字节） */
    private final int capacity;

    /** 数据区写入指针（从左往右增长） */
    private int nextDataWrite = 0;

    /** 索引区写入指针（从右往左增长），指向当前可写入索引的起始位置 */
    private int nextIndexWrite;

    /** 当前缓冲区中记录条数 */
    private int recordCount = 0;

    /** 触发溢写的阈值（容量占比） */
    private final float spillThreshold;

    /**
     * 构造指定容量的环形缓冲区
     * @param capacity 缓冲区容量（字节）
     */
    public RingBuffer(int capacity) {
        this(capacity, 0.8f);
    }

    /**
     * 构造指定容量和溢写阈值的环形缓冲区
     * @param capacity 缓冲区容量（字节）
     * @param spillThreshold 溢写阈值（0 ~ 1）
     */
    public RingBuffer(int capacity, float spillThreshold) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("缓冲区容量必须大于 0");
        }
        if (spillThreshold <= 0 || spillThreshold > 1) {
            throw new IllegalArgumentException("溢写阈值必须在 (0, 1] 之间");
        }
        this.capacity = capacity;
        this.buffer = new byte[capacity];
        this.nextIndexWrite = capacity;   // 索引区从最右端开始
        this.spillThreshold = spillThreshold;
    }

    /**
     * 写入一条记录（key-value 对）
     * @param partition 分区号
     * @param key       键（字符串，实际 Hadoop 是序列化后的字节）
     * @param value     值（字符串）
     * @return true 表示本次写入前触发了溢写，false 表示直接写入缓冲区
     */
    public synchronized boolean write(int partition, String key, String value) {
        byte[] keyBytes = key.getBytes();
        byte[] valueBytes = value.getBytes();

        // 每条索引固定 16 字节：partition + keystart + valstart + vallength
        int indexSize = 16;
        int dataSize = keyBytes.length + valueBytes.length;

        if (dataSize + indexSize > capacity) {
            throw new IllegalArgumentException("单条记录数据+索引大小超过缓冲区容量");
        }

        // 如果写入会导致使用率超过阈值，则先触发溢写
        if (needSpill(dataSize, indexSize)) {
            spill();
            // 溢写后空间已释放，可以继续写入（若仍然不够会在此次写入时再抛异常）
            return true;
        }

        // 1. 数据区写入 key + value（顺序存储）
        int keystart = nextDataWrite;                     // key 起始偏移
        System.arraycopy(keyBytes, 0, buffer, keystart, keyBytes.length);
        int valstart = nextDataWrite + keyBytes.length;   // value 起始偏移
        System.arraycopy(valueBytes, 0, buffer, valstart, valueBytes.length);
        nextDataWrite += dataSize;                        // 数据区指针右移

        // 2. 索引区写入元数据（从右往左），顺序：partition, keystart, valstart, vallength
        nextIndexWrite -= indexSize;
        writeInt(nextIndexWrite, partition);               // partition
        writeInt(nextIndexWrite + 4, keystart);            // keystart
        writeInt(nextIndexWrite + 8, valstart);            // valstart
        writeInt(nextIndexWrite + 12, valueBytes.length);  // vallength

        recordCount++;
        return false;
    }

    /**
     * 判断是否需要溢写
     * @param dataSize  待写入数据字节数
     * @param indexSize 待写入索引字节数
     * @return true 表示需要溢写
     */
    private boolean needSpill(int dataSize, int indexSize) {
        int used = nextDataWrite + (capacity - nextIndexWrite);      // 已用总字节
        return (used + dataSize + indexSize) > capacity * spillThreshold;
    }

    /**
     * 溢写缓冲区中的所有数据（模拟写磁盘）
     * 排序规则：按分区号升序 → 相同分区内按 key 的原始字节无符号升序
     */
    private void spill() {
        System.out.println("【溢写】当前记录数：" + recordCount
                + "，数据区已用：" + nextDataWrite
                + " 字节，索引区已用：" + (capacity - nextIndexWrite) + " 字节");

        // 1. 从索引区读取所有记录的元数据
        List<MetaEntry> metaList = new ArrayList<>();
        int indexPos = nextIndexWrite;   // 索引区从 nextIndexWrite 开始连续存放
        for (int i = 0; i < recordCount; i++) {
            int partition = readInt(indexPos);
            int keystart  = readInt(indexPos + 4);
            int valstart  = readInt(indexPos + 8);
            int vallength = readInt(indexPos + 12);
            metaList.add(new MetaEntry(partition, keystart, valstart, vallength));
            indexPos += 16;
        }

        // 2. 排序：先分区，再按 key 字节比较（无符号字典序）
        metaList.sort(Comparator.comparingInt(MetaEntry::getPartition)
                .thenComparing(this::compareKeyBytes));

        // 3. 按顺序输出（模拟溢写文件）
        for (MetaEntry meta : metaList) {
            int keyLen = meta.valstart - meta.keystart;
            String key = new String(buffer, meta.keystart, keyLen);
            String value = new String(buffer, meta.valstart, meta.vallength);
            System.out.println("分区 " + meta.partition + " -> " + key + " : " + value);
        }

        // 4. 重置缓冲区
        nextDataWrite = 0;
        nextIndexWrite = capacity;
        recordCount = 0;
    }

    /**
     * 比较两个元数据记录对应的 key 字节（无符号逐字节比较）
     */
    private int compareKeyBytes(MetaEntry a, MetaEntry b) {
        int lenA = a.valstart - a.keystart;
        int lenB = b.valstart - b.keystart;
        int minLen = Math.min(lenA, lenB);
        for (int i = 0; i < minLen; i++) {
            int cmp = (buffer[a.keystart + i] & 0xFF) - (buffer[b.keystart + i] & 0xFF);
            if (cmp != 0) {
                return cmp;
            }
        }
        return lenA - lenB;
    }

    /**
     * 手动触发最终溢写
     */
    public synchronized void flush() {
        if (recordCount > 0) {
            spill();
        }
    }

    /**
     * 获取已用空间大小（字节）
     */
    public int getUsedSize() {
        return nextDataWrite + (capacity - nextIndexWrite);
    }

    /**
     * 获取当前缓冲区中的记录数
     */
    public int getRecordCount() {
        return recordCount;
    }

    // --- 字节读写工具方法（小端序） ---

    private void writeInt(int offset, int value) {
        buffer[offset]     = (byte) (value & 0xFF);
        buffer[offset + 1] = (byte) ((value >> 8) & 0xFF);
        buffer[offset + 2] = (byte) ((value >> 16) & 0xFF);
        buffer[offset + 3] = (byte) ((value >> 24) & 0xFF);
    }

    private int readInt(int offset) {
        return (buffer[offset] & 0xFF)
                | ((buffer[offset + 1] & 0xFF) << 8)
                | ((buffer[offset + 2] & 0xFF) << 16)
                | ((buffer[offset + 3] & 0xFF) << 24);
    }

    /**
     * 索引元数据（对应 Hadoop kvmeta 的一条记录）
     */
    private static class MetaEntry {
        final int partition;
        final int keystart;
        final int valstart;
        final int vallength;

        MetaEntry(int partition, int keystart, int valstart, int vallength) {
            this.partition = partition;
            this.keystart = keystart;
            this.valstart = valstart;
            this.vallength = vallength;
        }

        int getPartition() {
            return partition;
        }
    }
}