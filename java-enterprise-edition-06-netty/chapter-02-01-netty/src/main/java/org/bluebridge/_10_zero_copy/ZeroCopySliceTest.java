package org.bluebridge._10_zero_copy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.bluebridge.utils.ByteBufUtil;


/**
 * netty中的零拷贝
 */
public class ZeroCopySliceTest {

    /**
     * 【零拷贝】的体现之一，对原始 ByteBuf 进行切片成多个 ByteBuf，切片后的 ByteBuf 并没有发生内存复制，还
     *  是使用原始 ByteBuf 的内存，切片后的 ByteBuf 维护独立的 read，write 指针
     * @param args
     */
    public static void main(String[] args) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(10);
        byteBuf.writeBytes(new byte[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j' });
        // 切片一
        ByteBuf byteBufSlice1 = byteBuf.slice(0, 5);
        byteBufSlice1.setByte(0, 'A');
        // 切片二
        ByteBuf byteBufSlice2 = byteBuf.slice(5, 5);
        byteBufSlice2.setByte(0, 'F');

        ByteBufUtil.debugRead(byteBuf);
        ByteBufUtil.debugRead(byteBufSlice1);
        ByteBufUtil.debugRead(byteBufSlice2);
    }

}
