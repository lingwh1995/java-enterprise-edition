package org.bluebridge.chapter_01_bytebuffer;

import org.bluebridge.ByteBufferUtil;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author lingwh
 * @desc   两个ByteBuffer读写测试
 * @date   2025/6/20 16:37
 */
public class _03_ByteBufferReadWriteTest {

    @Test
    public void testByteBufferReadWrite() {
        // 创建src ByteBuffer
        ByteBuffer source = ByteBuffer.allocate(10);
        source.put("hello".getBytes());
        ByteBufferUtil.debugAll(source);
        // 将source切换为读模式
        source.flip();

        // source的limit属性决定了target能从source中读取的数据长度
        source.limit(2);
        // 创建target ByteBuffer
        ByteBuffer target = ByteBuffer.allocate(5);
        target.put(source);
        ByteBufferUtil.debugAll(target);
    }

}
