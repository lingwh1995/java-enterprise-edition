package org.bluebridge.chapter_01_bytebuffer;

import org.bluebridge.ByteBufferUtil;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * ByteBuffer读数据测试
 */
public class _04_ByteBufferReadTest {

    @Test
    public void testByteBufferReadWrite() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd', 'e'});
        ByteBufferUtil.debugAll(buffer);
        // 将source切换为读模式
        buffer.flip();

        // 读取一个字节
        System.out.println((char)buffer.get());
        // 使用字节数组一次读取多个字节
        byte[] b = new byte[3];
        buffer.get(b);
        String s = new String(b);
        System.out.println(s);
    }

}
