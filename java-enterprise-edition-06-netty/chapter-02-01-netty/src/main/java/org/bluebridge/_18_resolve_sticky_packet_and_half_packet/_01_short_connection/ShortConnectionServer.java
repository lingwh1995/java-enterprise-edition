package org.bluebridge._18_resolve_sticky_packet_and_half_packet._01_short_connection;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lingwh
 * @desc 短连接解决黏包问题 服务端
 * @date 2025/10/11 10:43
 */

/**
 * 使用短连接解决黏包问题
 * 以解决粘包为例
 *    改客户端代码，每次发16字节后，断开连接，
 * 但是无法解决半包问题
 *    修改服务端代码，让netty的缓冲区最大为16字节
 */
@Slf4j
public class ShortConnectionServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup(4);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                .channel(NioServerSocketChannel.class)
                .group(boss, worker)
                 // 设置 netty 的接收缓冲区大小， 如果要查看短连接不能解决半包问题，放开下面一行的注释
                 //.childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(16, 16, 16))
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
                        });
                    }
                });
            ChannelFuture channelFuture = serverBootstrap.bind(HOST, PORT);
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
