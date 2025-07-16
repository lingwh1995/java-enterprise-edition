package org.bluebridge.chapter_01_bytebuffer;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.ByteBufferUtil;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author lingwh
 * @desc   ByteBuffer和字符串互相转换
 * @date   2025/6/22 14:23
 */
@Slf4j(topic = "·")
public class _06_ByteBufferAndStringTransferTest {

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
        log.info("str1 = {}", str1);

        // 2.字符串转为ByteBuffer方式二：已经直接切换为读模式
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("abcde");
        ByteBufferUtil.debugAll(buffer2);
        String str2 = StandardCharsets.UTF_8.decode(buffer2).toString();
        log.info("str2 = {}", str2);

        // 3.字符串转为ByteBuffer方式三：已经直接切换为读模式
        ByteBuffer buffer3 = ByteBuffer.wrap("abcde".getBytes());
        ByteBufferUtil.debugAll(buffer3);
        String str3 = StandardCharsets.UTF_8.decode(buffer3).toString();
        log.info("str3 = {}", str3);
    }

}
