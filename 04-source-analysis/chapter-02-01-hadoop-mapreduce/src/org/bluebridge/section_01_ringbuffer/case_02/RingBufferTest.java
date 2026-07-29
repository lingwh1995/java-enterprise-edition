package org.bluebridge.section_01_ringbuffer.case_02;

import org.bluebridge.mapreduce.case_04_ringbuffer.RingBuffer;

/**
 * 简易环形缓冲区测试类
 *
 * @author lingwh
 * @date 2026/7/28 17:52
 */
public class RingBufferTest {

    /**
     * 测试入口
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        // 创建一个很小的缓冲区，便于触发溢写
        RingBuffer ringBuffer = new RingBuffer(512, 0.8f);

        System.out.println("--- 初始状态 ---");
        ringBuffer.printBufferLayout();

        System.out.println("\n--- 开始写入数据 ---");

        // 写入多条记录，分区号为 hash 值取模
        String[] keys = {"apple", "banana", "cat", "dog", "egg", "fish", "goat", "hat", "ice", "juice"};
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            int partition = Math.floorMod(key.hashCode(), 3);
            String value = "value-" + i;
            boolean spilled = ringBuffer.collect(partition, key, value);
            System.out.println("写入：" + key + " -> " + value
                    + "（分区 " + partition + "）"
                    + (spilled ? "，触发溢写" : "")
                    + "，已用空间：" + ringBuffer.getUsedSize() + " 字节");
            if (spilled) {
                ringBuffer.printBufferLayout();
            }
        }

        System.out.println("\n--- 写入完成，最终状态 ---");
        ringBuffer.printBufferLayout();

        System.out.println("\n--- 最终 flush ---");
        ringBuffer.flush();

        System.out.println("\n--- 测试完成 ---");
    }
}
