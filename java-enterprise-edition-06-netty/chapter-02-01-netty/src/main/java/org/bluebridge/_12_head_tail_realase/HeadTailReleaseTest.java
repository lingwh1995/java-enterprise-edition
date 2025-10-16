package org.bluebridge._12_head_tail_realase;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.bluebridge.utils.ByteBufUtil;

/**
 * HeadContext 和 TailContext 释放资源
 *      应当理解到： netty中的创建的Bytebuf使用完成后最终要被释放，而不是什么处理都不做
 */
public class HeadTailReleaseTest {

    /**
     * DefaultChannelPipeline.HeadContext中
     *
     * @Override
     * public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
     *      unsafe.write(msg, promise);
     * }
     *
     * 执行了释放资源的方法
     */

    /**
     * DefaultChannelPipeline.TailContext中
     *
     * @Override
     * public void channelRead(ChannelHandlerContext ctx, Object msg) {
     *      onUnhandledInboundMessage(ctx, msg);
     * }
     *
     * 执行了释放资源的方法
     */

    /**
     * 测试 byteBuf.release(); 方法
     * @param args
     */
    public static void main(String[] args) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeBytes(new byte[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j' });
        // byteBuf引用计数加1
        byteBuf.retain();
        // byteBuf引用计数减1，当byteBuf的引用计数为0时，会释放资源，虽然byteBuf对象还存在，再次调用debugAll()方法会抛出异常
        byteBuf.release();
        ByteBufUtil.debugAll(byteBuf);
    }

}
