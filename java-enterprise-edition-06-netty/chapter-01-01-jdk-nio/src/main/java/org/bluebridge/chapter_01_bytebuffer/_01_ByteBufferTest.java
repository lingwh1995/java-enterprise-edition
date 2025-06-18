package org.bluebridge.chapter_01_bytebuffer;

import org.bluebridge.ByteBufferUtil;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * ByteBuffer读写数据
 */
public class _01_ByteBufferTest {

    @Test
    public void testByteBuffer() {
        // 创建ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(10);
        ByteBufferUtil.debugAll(buffer, 1);
        // 给ByteBuffer中放入一个数据
        buffer.put((byte)0x61);// 'a'
        ByteBufferUtil.debugAll(buffer, 2);
        // 给ByteBuffer中放入多个数据
        buffer.put(new byte[]{(byte)0x62, (byte)0x63, (byte)0x64, (byte)0x65});
        ByteBufferUtil.debugAll(buffer, 3);

        /**
         * 调用flip()方法后 =>
         *  1.将缓冲区切换为读模式
         *  2.limit = position;  // 设置可读取数据的终点
         *  3.position = 0;      // 复位读取起始位置
         *  4.mark = -1;         // 清除标记
         */
        buffer.flip();
        ByteBufferUtil.debugAll(buffer, 4);
        /**
         * 调用get()方法后 =>
         *  1.position = position + 1;      // position位置会后移一位
         */
        System.out.println(buffer.get()); // 读取到的数据会转换为10进制数
        ByteBufferUtil.debugAll(buffer, 5);

        /**
         * 调用compact()方法后 =>
         *  1.将缓冲区切换为写模式
         *  2.position‌：移动到上次读取的末尾位置
         *  3.limit‌：设置为缓冲区的容量（capacity）
         */
        buffer.compact();
        ByteBufferUtil.debugAll(buffer, 6);
        buffer.put(new byte[]{(byte)0x66, (byte)0x67, (byte)0x68});
        ByteBufferUtil.debugAll(buffer, 7);
        buffer.compact();
        ByteBufferUtil.debugAll(buffer, 8);
    }

}
