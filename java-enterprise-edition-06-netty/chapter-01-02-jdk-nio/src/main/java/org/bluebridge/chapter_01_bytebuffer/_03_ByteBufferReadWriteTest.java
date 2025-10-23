package org.bluebridge.chapter_01_bytebuffer;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.ByteBufferUtil;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author lingwh
 * @desc 两个 ByteBuffer 读写测试
 * @date 2025/6/20 16:37
 */
@Slf4j
public class _03_ByteBufferReadWriteTest {

    @Test
    public void testByteBufferReadWrite() {
        // 创建src ByteBuffer
        ByteBuffer source = ByteBuffer.allocate(10);
        source.put("hello".getBytes());
        ByteBufferUtil.debugAll(source);
        // 将 source 切换为读模式
        source.flip();

        // source 的 limit 属性决定了 target 能从 source 中读取的数据长度
        source.limit(2);
        // 创建 target ByteBuffer
        ByteBuffer target = ByteBuffer.allocate(5);
        target.put(source);
        ByteBufferUtil.debugAll(target);
    }

}
