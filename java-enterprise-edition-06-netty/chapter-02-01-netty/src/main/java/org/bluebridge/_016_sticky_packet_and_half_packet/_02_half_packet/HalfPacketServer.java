package org.bluebridge._016_sticky_packet_and_half_packet._02_half_packet;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lingwh
 * @desc Netty半包测试 服务端
 * @date 2025/10/11 10:43
 */

/**
 * 黏包现象分析
 *   客户端总共发送10次消息，每次消息是16字节
 *   服务器端一次就接收了160个字节，而非分10次接收，这样就发生了黏包现象
 *
 * 半包
 *    现象
 *       发送 abcdef，接收 abc def
 *    原因
 *       滑动窗口：假设接收方的窗口只剩了 128 bytes，发送方的报文大小是 256 bytes，这时放不下了，只能先发送前 128 bytes，等待 ack 后才能发送剩余部分，这就造成了半包
 *       MSS 限制：当发送的数据超过 MSS 限制后，会将数据切分发送，就会造成半包
 *    本质原因
 *       TCP 是流式协议，消息无边界，所以接收方无法知道消息的边界，只能根据滑动窗口大小来判断是否接收完整
 */
@Slf4j
public class HalfPacketServer {

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup(4);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                .channel(NioServerSocketChannel.class)
                 // 设置系统接收缓冲区（即滑动窗口）大小为10字节
                .option(ChannelOption.SO_RCVBUF, 3)
                .group(boss, worker)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                        pipeline.addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                log.info("connected {}", ctx.channel());
                                super.channelActive(ctx);
                            }

                            @Override
                            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                log.info("disconnect {}", ctx.channel());
                                super.channelInactive(ctx);
                            }

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buffer = (ByteBuf) msg;
                                log.info("接收到的字节数: {}", buffer.readableBytes());
                                // 处理半包逻辑
                                super.channelRead(ctx, msg);
                            }
                        });
                    }
                });
            ChannelFuture channelFuture = serverBootstrap.bind(8080);
            log.info("{} binding......", channelFuture.channel());
            channelFuture.sync();
            log.info("{} binding successful......", channelFuture.channel());
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error......", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
            log.info("stopped......");
        }
    }

}