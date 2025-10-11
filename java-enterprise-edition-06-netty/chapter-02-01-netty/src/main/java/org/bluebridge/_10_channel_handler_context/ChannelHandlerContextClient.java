package org.bluebridge._10_channel_handler_context;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author lingwh
 * @desc 使用ChannelHandlerContext接口的子类来创建Bytebuf 客户端
 * @date 2025/10/11 15:39
 */

/**
 * 在Netty编程中通常使用ChannelHandlerContext接口的子类来创建Bytebuf，而不是使用ByteBufAllocator或Unpooled方式创建ByteBuf
 */
@Slf4j
public class ChannelHandlerContextClient {

    public static void main(String[] args) throws InterruptedException {
        Channel channel = new Bootstrap()
            .group(new NioEventLoopGroup())
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) {
                            // 使用ChannelHandlerContext接口的子类来创建Bytebuf
                            ByteBuf byteBuf = ctx.alloc().buffer();
                            byteBuf.writeBytes(new byte[] { 'a', 'b', 'c', 'd', 'e'});
                            ctx.writeAndFlush(byteBuf);
                        }
                    });
                }
            })
            .connect("localhost", 8080)
            .sync()
            .channel();
    }

}
