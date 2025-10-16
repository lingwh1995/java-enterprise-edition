package org.bluebridge._13_zero_copy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.utils.ByteBufUtil;
import org.junit.Test;


/**
 * netty中的零拷贝
 */
@Slf4j
public class NettyZeroCopyTest {

    /**
     * 将一个大的ByteBuf分片成小的ByteBuf
     *
     * 【零拷贝】的体现之一，对原始 ByteBuf 进行切片成多个 ByteBuf，切片后的 ByteBuf 并没有发生内存复制，还
     *  是使用原始 ByteBuf 的内存，切片后的 ByteBuf 维护独立的 read，write 指针
     */
    @Test
    public void testByteBufSlice() {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(10);
        byteBuf.writeBytes(new byte[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j' });
        // 切片一
        ByteBuf byteBufSlice1 = byteBuf.slice(0, 5);
        byteBufSlice1.setByte(0, 'A');
        // 切片二
        ByteBuf byteBufSlice2 = byteBuf.slice(5, 5);
        byteBufSlice2.setByte(0, 'F');

        ByteBufUtil.debugAll(byteBuf);
        ByteBufUtil.debugRead(byteBufSlice1);
        ByteBufUtil.debugRead(byteBufSlice2);
    }

    /**
     * 截取原ByteBuf的全部内容
     *
     * 【零拷贝】的体现之一，就好比截取了原始 ByteBuf 所有内容，并且没有 max capacity 的限制，也是与原
     *  始 ByteBuf 使用同一块底层内存，只是读写指针是独立的
     */
    @Test
    public void testByteBufDuplicate() {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(10);
        byteBuf.writeBytes(new byte[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j' });
        // 复制原始 ByteBuf
        ByteBuf byteBufDuplicate = byteBuf.duplicate();
        byteBufDuplicate.setByte(0, 'A');
        ByteBufUtil.debugAll(byteBuf);
        ByteBufUtil.debugAll(byteBufDuplicate);
        // 从截取的 ByteBuf 中读取一个字节，会改变截取 ByteBuf 的 readerIndex，不会改变原始 ByteBuf 的 readerIndex，因为调用duplicate()方法复制的 ByteBuf 读写指针是独立的
        char c = (char)byteBufDuplicate.readByte();
        log.info("c = {}", c);
        ByteBufUtil.debugAll(byteBuf);
        ByteBufUtil.debugAll(byteBufDuplicate);
    }

    /**
     * 零拷贝方式合并多个ByteBuf
     */
    @Test
    public void testByteBufComposite() {
        ByteBuf byteBuf1 = ByteBufAllocator.DEFAULT.buffer(10);
        byteBuf1.writeBytes(new byte[] { 'a', 'b', 'c', 'd', 'e' });
        ByteBufUtil.debugAll(byteBuf1);
        ByteBuf byteBuf2 = ByteBufAllocator.DEFAULT.buffer(10);
        byteBuf2.writeBytes(new byte[] { 'f', 'g', 'h', 'i', 'j' });
        ByteBufUtil.debugAll(byteBuf2);

        /*
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(20);
        // 合并两个ByteBuf，writeBytes()会发生真正的数据复制，而不是零拷贝，下面方法就发生了两次数据复制
        byteBuf.writeBytes(byteBuf1).writeBytes(byteBuf2);
        ByteBufUtil.debugAll(byteBuf);
        */

        CompositeByteBuf byteBufs = ByteBufAllocator.DEFAULT.compositeBuffer();
        /*
        byteBufs.addComponent(byteBuf1);
        byteBufs.addComponent(byteBuf2);
        */
        // true 表示增加新的 ByteBuf 自动递增 write index, 否则 write index 会始终为 0
        byteBufs.addComponents(true, byteBuf1, byteBuf2);
        ByteBufUtil.debugAll(byteBufs);
    }

}
