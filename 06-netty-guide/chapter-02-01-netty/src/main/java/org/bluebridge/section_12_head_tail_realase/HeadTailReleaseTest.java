package org.bluebridge.section_12_head_tail_realase;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.bluebridge.utils.ByteBufUtil;

/**
 * HeadContext 和 TailContext 释放资源
 *
 * 应当理解到： netty 中的创建的 Bytebuf 使用完成后最终必然要被释放，而不是什么处理都不做
 *
 * @author lingwh
 * @date 2025/10/11 16:20
 */
public class HeadTailReleaseTest {

    /**
     * DefaultChannelPipeline.HeadContext 中
     *
     * @Override
     * public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
     *      unsafe.write(msg, promise);
     * }
     *
     * 执行了释放资源的方法
     */

    /**
     * DefaultChannelPipeline.TailContext 中
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
     *
     * @param args
     */
    public static void main(String[] args) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeBytes(new byte[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j' });
        // byteBuf 引用计数加 1
        byteBuf.retain();
        // byteBuf 引用计数减 1，当 byteBuf 的引用计数为 0 时，会释放资源，虽然 byteBuf 对象还存在，再次调用 debugAll() 方法会抛出异常
        byteBuf.release();
        ByteBufUtil.debugAll(byteBuf);
    }
}
