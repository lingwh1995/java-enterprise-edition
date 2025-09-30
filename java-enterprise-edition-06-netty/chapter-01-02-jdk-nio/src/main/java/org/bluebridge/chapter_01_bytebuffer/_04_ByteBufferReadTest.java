package org.bluebridge.chapter_01_bytebuffer;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.ByteBufferUtil;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author lingwh
 * @desc   ByteBuffer读数据测试
 * @date   2025/6/21 10:19
 */
@Slf4j
public class _04_ByteBufferReadTest {

    /**
     * 每次读取一个字节
     * 每次读取多个字节
     */
    @Test
    public void testByteBufferReadWrite() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{ 'a', 'b', 'c', 'd', 'e' });
        ByteBufferUtil.debugAll(buffer);
        // 将source切换为读模式
        buffer.flip();

        // 读取一个字节
        log.info("(char)buffer.get(): {}", (char)buffer.get());
        // 使用字节数组一次读取多个字节
        byte[] b = new byte[3];
        buffer.get(b);
        String s = new String(b);
        log.info("s: {}", s);
    }

}
