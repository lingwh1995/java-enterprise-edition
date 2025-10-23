package org.bluebridge.chapter_01_bytebuffer;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.ByteBufferUtil;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author lingwh
 * @desc ByteBuffer 基础使用
 * @date 2025/6/20 9:38
 */
@Slf4j
public class _01_ByteBufferHelloWorldTest {

    /**
     * ByteBuffer 基础使用
     */
    @Test
    public void testByteBufferHelloWorld() {
        // 创建 ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(10);
        ByteBufferUtil.debugAll(buffer, 1);
        // 给 ByteBuffer 中放入一个字符 'a'
        buffer.put((byte)0x61);
        ByteBufferUtil.debugAll(buffer, 2);
        // 给 ByteBuffer 中放入多个数据
        buffer.put(new byte[]{(byte)0x62, (byte)0x63, (byte)0x64, (byte)0x65});
        ByteBufferUtil.debugAll(buffer, 3);

        /**
         * 调用 flip() 方法后 =>
         *  1.将缓冲区切换为读模式
         *  2.limit = position;  // 设置可读取数据的终点
         *  3.position = 0;      // 复位读取起始位置
         *  4.mark = -1;         // 清除标记
         */
        buffer.flip();
        ByteBufferUtil.debugAll(buffer, 4);
        /**
         * 调用 get() 方法后 =>
         *  // position 位置会后移一位
         *  1.position = position + 1;
         */
        // 读取到的数据会转换为10进制数
        log.info("buffer.get() = {}", (char)buffer.get());
        log.info("buffer.get() = {}", (char)buffer.get());
        ByteBufferUtil.debugAll(buffer, 5);

        /**
         * 调用 compact() 方法后 =>
         *  1.将缓冲区切换为写模式
         *  2.position‌：移动到上次读取的末尾位置，已经读取过的部分直接压缩掉
         *  3.limit‌：设置为缓冲区的容量（capacity）
         */
        buffer.compact();
        ByteBufferUtil.debugAll(buffer, 6);
        buffer.put(new byte[]{(byte)0x66, (byte)0x67, (byte)0x68});
        ByteBufferUtil.debugAll(buffer, 7);
        buffer.compact();
        ByteBufferUtil.debugAll(buffer, 8);
        buffer.compact();
        ByteBufferUtil.debugAll(buffer, 9);
        buffer.compact();
        ByteBufferUtil.debugAll(buffer, 10);
    }

}
