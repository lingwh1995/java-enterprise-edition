package org.bluebridge._10_zero_copy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.utils.ByteBufUtil;

/**
 * netty中的零拷贝
 */
@Slf4j
public class ZeroCopyDuplicateTest {

    /**
     * 【零拷贝】的体现之一，就好比截取了原始 ByteBuf 所有内容，并且没有 max capacity 的限制，也是与原
     *  始 ByteBuf 使用同一块底层内存，只是读写指针是独立的
     * @param args
     */
    public static void main(String[] args) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(10);
        byteBuf.writeBytes(new byte[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j' });
        // 复制原始 ByteBuf
        ByteBuf byteBufDuplicate = byteBuf.duplicate();
        byteBufDuplicate.setByte(0, 'A');
        ByteBufUtil.debugRead(byteBuf);
        ByteBufUtil.debugRead(byteBufDuplicate);
        // 从截取的 ByteBuf 中读取一个字节，会改变截取 ByteBuf 的 readerIndex，不会改变原始 ByteBuf 的 readerIndex，因为调用duplicate()方法复制的 ByteBuf 读写指针是独立的
        char c = (char)byteBufDuplicate.readByte();
        log.info("c = {}", c);
        ByteBufUtil.debugRead(byteBuf);
        ByteBufUtil.debugRead(byteBufDuplicate);
    }

}
