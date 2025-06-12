package org.bluebridge.chapter_01;

import org.bluebridge.ByteBufferUtil;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * ByteBuffer的常用方法：
 *   rewind()  把position移动到0索引位置
 *   mark() & reset()   mark 做一个标记，记录position位置，reset是将position重置到mark位置
 */
public class _03_ByteBufferReadTest {

    @Test
    public void testByteBufferRead() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd', 'e'});
        buffer.flip();

        // 从头开始读
        buffer.get(new byte[4]);
        ByteBufferUtil.debugAll(buffer);

        buffer.rewind(); // 把position移动到0索引位置
        ByteBufferUtil.debugAll(buffer);
        System.out.println((char)buffer.get());

        buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd', 'e'});
        // mark & reset
        // mark 做一个标记，记录position位置，reset是将position重置到mark位置
        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
        buffer.mark(); // 加标记，索引2位置
        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
        ByteBufferUtil.debugAll(buffer);
        buffer.reset(); // 将position重置到索引2位置
        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
        ByteBufferUtil.debugAll(buffer);
    }

}
