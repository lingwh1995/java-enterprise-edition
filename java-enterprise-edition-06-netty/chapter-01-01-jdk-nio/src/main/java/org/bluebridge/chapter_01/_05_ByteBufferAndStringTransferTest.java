package org.bluebridge.chapter_01;

import org.bluebridge.ByteBufferUtil;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * ByteBuffer和字符串互相转换
 */
public class _05_ByteBufferAndStringTransferTest {

    /**
     * 字符串转换为ByteBuffer
     */
    @Test
    public void testByteBufferAndStringTransfer() {
        // 1.字符串转为ByteBuffer方式一：没有直接切换为读模式
        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        buffer1.put("abcde".getBytes());
        ByteBufferUtil.debugAll(buffer1);
        // 手动切换为读模式
        buffer1.flip();
        String str1 = StandardCharsets.UTF_8.decode(buffer1).toString();
        System.out.println("str1 = " + str1);

        // 2.字符串转为ByteBuffer方式二：已经直接切换为读模式
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("abcde");
        ByteBufferUtil.debugAll(buffer2);
        String str2 = StandardCharsets.UTF_8.decode(buffer2).toString();
        System.out.println("str2 = " + str2);

        // 3.字符串转为ByteBuffer方式三：已经直接切换为读模式
        ByteBuffer buffer3 = ByteBuffer.wrap("abcde".getBytes());
        ByteBufferUtil.debugAll(buffer3);
        String str3 = StandardCharsets.UTF_8.decode(buffer3).toString();
        System.out.println("str3 = " + str3);
    }

}
