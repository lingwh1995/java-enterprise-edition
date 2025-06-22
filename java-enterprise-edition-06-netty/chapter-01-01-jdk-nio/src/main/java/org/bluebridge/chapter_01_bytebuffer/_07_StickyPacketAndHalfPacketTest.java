package org.bluebridge.chapter_01_bytebuffer;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.ByteBufferUtil;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * 处理黏包和半包
 */
@Slf4j
public class _07_StickyPacketAndHalfPacketTest {

    /**
     * 网络上有多条数据发送给服务端，数据之间使用 \n 进行分隔
     * 但由于某种原因这些数据在接收时，被进行了重新组合，例如原始数据有3条为
     *
     *   Hello,world\n
     *   I'm zhangsan\n
     *   How are you?\n
     *
     * 变成了下面的两个 byteBuffer (黏包，半包)
     *
     *   Hello,world\nI'm zhangsan\nHo
     *   w are you?\n
     *
     * 现在要求你编写程序，将错乱的数据恢复成原始的按 \n 分隔的数据
     */
    @Test
    public void testStickyPacketAndHalfPacket() {
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("\n".getBytes());
        ByteBufferUtil.debugAll(source);
        //                     11            24
        source.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        splitPacket(source);

        source.put("w are you?\nhaha!\n".getBytes());
        splitPacket(source);
    }

    /**
     * 拆包
     * @param source
     */
    private static void splitPacket(ByteBuffer source) {
        source.flip();
        int oldLimit = source.limit();
        for (int i = 0; i < oldLimit; i++) {
            log.debug("source.get(i) = {}", source.get(i));
            if (source.get(i) == '\n') {
                System.out.println(i);
                ByteBuffer target = ByteBuffer.allocate(i + 1 - source.position());
                // 0 ~ limit
                source.limit(i + 1);
                target.put(source); // 从source 读，向 target 写
                ByteBufferUtil.debugAll(target);
                source.limit(oldLimit);
            }
        }
        source.compact();
    }

}
