package org.bluebridge;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

/**
 * ByteBuffer和字符串互相转换
 */
public class ByteBufferAndStringTransferTest {

    /**
     * 字符串转换为ByteBuffer
     */
    @Test
    public void testStringToByteBuffer() {
        // 1.字符串转为ByteBuffer方式一：没有直接切换为读模式
        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        buffer1.put("abcde".getBytes());
        ByteBufferUtil.debugAll(buffer1);

        // 2.字符串转为ByteBuffer方式二：已经直接切换为读模式
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("abcde");
        ByteBufferUtil.debugAll(buffer2);

        // 3.字符串转为ByteBuffer方式三：已经直接切换为读模式
        ByteBuffer buffer3 = ByteBuffer.wrap("abcde".getBytes());
        ByteBufferUtil.debugAll(buffer3);
    }

    /**
     * ByteBuffer转换为字符串
     */
    @Test
    public void testByteBufferToString() {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put(new byte[]{'a', 'b', 'c', 'd', 'e'});
        buffer.flip();
        String str = StandardCharsets.UTF_8.decode(buffer).toString();
        System.out.println("str = " + str);
    }
}
