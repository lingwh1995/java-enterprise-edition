package org.bluebridge._11_unpooled;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

public class UnpooledTest {

    public static void main(String[] args) {
        ByteBuf byteBuf1 = ByteBufAllocator.DEFAULT.buffer(5);
        byteBuf1.writeBytes(new byte[]{ 1, 2, 3, 4, 5 });
        ByteBuf byteBuf2 = ByteBufAllocator.DEFAULT.buffer(5);
        byteBuf2.writeBytes(new byte[]{ 6, 7, 8, 9, 10 });

        // 当包装 ByteBuf 个数超过一个时，底层使用了 CompositeByteBuf，wrappedBuffer()是零拷贝方法
        ByteBuf byteBuf3 = Unpooled.wrappedBuffer(byteBuf1, byteBuf2);
        System.out.println(ByteBufUtil.prettyHexDump(byteBuf3));
    }

}
