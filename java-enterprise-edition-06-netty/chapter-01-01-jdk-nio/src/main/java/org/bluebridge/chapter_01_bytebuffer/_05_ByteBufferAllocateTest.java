package org.bluebridge.chapter_01_bytebuffer;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author lingwh
 * @desc   ByteBuffer分配空间的两种方式
 * @date   2025/6/22 11:19
 */
@Slf4j(topic = "·")
public class _05_ByteBufferAllocateTest {

    @Test
    public void testByteBufferAllocate() {
        /**
         * 非直接内存: jvm堆内存，性能低，申请快，读写效率较低，容易受到GC的影响
         *
         */
        log.info("非直接内存: {}", ByteBuffer.allocate(10).getClass());

        /**
         * 直接内存:物理内存，性能高，申请慢，读写效率高（少一次拷贝），不会受GC的影响，适用于数据量大，IO生命周期长或者IO次数频繁
         */
        log.info("直接内存: {}", ByteBuffer.allocateDirect(10).getClass());
    }

}
