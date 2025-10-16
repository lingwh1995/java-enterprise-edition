package org.bluebridge._17_resolve_sticky_packet_and_half_packet._03_delimiter_based_frame_decoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lingwh
 * @desc 自定义分隔符解码器解决黏包半包问题 服务端
 * @date 2025/10/14 11:47
 */

/**
 * 缺点，处理字符数据比较合适，但如果内容本身包含了分隔符（字节数据常常会有此情况），那么就会解析错误
 */
@Slf4j
public class DelimiterBasedFrameDecoderServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup(4);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                .channel(NioServerSocketChannel.class)
                .group(boss, worker)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 使用自定义符号作为作为分隔符，如果超出指定长度仍未出现分隔符，则抛出异常
                        ByteBuf buf= ByteBufAllocator.DEFAULT.buffer();
                        buf.writeByte('$');
                        pipeline.addLast(new DelimiterBasedFrameDecoder(1024, buf));
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
