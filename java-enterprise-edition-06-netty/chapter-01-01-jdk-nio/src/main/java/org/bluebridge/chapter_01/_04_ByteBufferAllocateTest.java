package org.bluebridge.chapter_01;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * ByteBuffer分配空间的两种方式
 */
public class _04_ByteBufferAllocateTest {

    @Test
    public void testByteBufferAllocate() {
        /**
         * java堆内存，读写效率较低，容易受到GC的影响
         */
        System.out.println(ByteBuffer.allocate(10).getClass());

        /**
         * 直接内存，读写效率高（少一次拷贝），不会受GC的影响
         */
        System.out.println(ByteBuffer.allocateDirect(10).getClass());
    }

}
