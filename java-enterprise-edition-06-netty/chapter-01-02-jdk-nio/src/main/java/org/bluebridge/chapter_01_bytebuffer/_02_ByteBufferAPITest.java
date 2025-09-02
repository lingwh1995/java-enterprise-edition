package org.bluebridge.chapter_01_bytebuffer;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.ByteBufferUtil;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author lingwh
 * @desc   ByteBuffer的常用方法
 * @date   2025/6/20 11:42
 */

/**
 * ByteBuffer的常用方法：
 *   position()  设置postion的值
 *   limit()     设置limit的值
 *
 *   rewind()    把position移动到0索引位置
 *   mark()      mark 做一个标记，记录position位置
 *   reset()     reset是将position重置到mark位置
 *
 *   clear()     切换为写模式
 *   put()       写入数据
 */
@Slf4j(topic = "·")
public class _02_ByteBufferAPITest {

    /**
     * position() 设置postion的值
     * limit() 设置limit的值
     */
    @Test
    public void testByteBufferPositionAndLimit() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // 向ByteBuffer中写入数据
        buffer.put(new byte[]{ 'a', 'b', 'c', 'd', 'e' });
        ByteBufferUtil.debugAll(buffer, 1);
        // 切换为读模式
        buffer.flip();
        ByteBufferUtil.debugAll(buffer, 2);

        // 读取第 position=0 时的字符
        log.info("position值为0 = {}", (char)buffer.get());
        ByteBufferUtil.debugAll(buffer, 3);

        // 读取第 position=1 时的字符
        log.info("position值为1 = {}", (char)buffer.get());
        ByteBufferUtil.debugAll(buffer, 4);

        // 设置position的值
        buffer.position(0);
        ByteBufferUtil.debugAll(buffer, 5);
        log.info("position值重设 = {}", (char)buffer.get());
        ByteBufferUtil.debugAll(buffer, 6);

        // 设置limit的值
        buffer.limit(3);
        ByteBufferUtil.debugAll(buffer, 7);
    }

    /**
     * rewind()  把position移动到0索引位置
     * mark() & reset()   mark 做一个标记，记录position位置，reset是将position重置到mark位置
     */
    @Test
    public void testByteBufferRewindMarkAndReset() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // 向ByteBuffer中写入数据
        buffer.put(new byte[]{'a', 'b', 'c', 'd', 'e'});
        // 切换为读模式
        buffer.flip();

        // 从头开始读
        buffer.get(new byte[4]);
        ByteBufferUtil.debugAll(buffer, 1);

        buffer.rewind(); // 把position移动到0索引位置
        ByteBufferUtil.debugAll(buffer, 2);
        log.info("(char)buffer.get() = {}", (char)buffer.get());

        buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd', 'e'});
        buffer.flip();
        ByteBufferUtil.debugAll(buffer, 3);
        // mark & reset
        // mark 做一个标记，记录position位置，reset是将position重置到mark位置
        log.info("(char)buffer.get() = {}", (char)buffer.get());
        log.info("(char)buffer.get() = {}", (char)buffer.get());
        // 加标记，索引2位置
        buffer.mark();
        log.info("(char)buffer.get() = {}", (char)buffer.get());
        log.info("(char)buffer.get() = {}", (char)buffer.get());
        ByteBufferUtil.debugAll(buffer, 4);
        // 将position重置到索引2位置
        buffer.reset();
        log.info("(char)buffer.get() = {}", (char)buffer.get());
        log.info("(char)buffer.get() = {}", (char)buffer.get());
        ByteBufferUtil.debugAll(buffer, 5);
    }

    /**
     * clear()     切换为写模式
     * put()       写入数据
     */
    @Test
    public void testByteBufferClearAndPut() {
        // 创建ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(10);
        ByteBufferUtil.debugAll(buffer, 1);
        // 给ByteBuffer中放入数据
        buffer.put(new byte[]{'a', 'b', 'c', 'd', 'e'});
        ByteBufferUtil.debugAll(buffer, 2);
        // 切换为写模式，特别注意：切换为写模式，只是把position的位置设置0，里面的数据并没有发生变化
        buffer.clear();
        ByteBufferUtil.debugAll(buffer, 3);
        // 写入数据，调用下面的方法只会覆盖第一个位置的值，不会覆盖后面的数据
        buffer.put(new byte[]{'f'});
        ByteBufferUtil.debugAll(buffer, 4);
    }

}
