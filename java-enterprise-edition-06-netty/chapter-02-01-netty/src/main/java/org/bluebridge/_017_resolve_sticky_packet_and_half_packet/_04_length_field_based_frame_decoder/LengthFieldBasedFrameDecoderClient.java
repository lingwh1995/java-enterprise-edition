package org.bluebridge._017_resolve_sticky_packet_and_half_packet._04_length_field_based_frame_decoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author lingwh
 * @desc 预设长度解码器解决黏包半包问题 客户端
 * @date 2025/10/14 11:48
 */
@Slf4j
public class LengthFieldBasedFrameDecoderClient {

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    log.info("connected......");
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                    // 预设长度解码器
                    pipeline.addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 0));
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        log.info("sending......");
                        ByteBuf byteBuf = ctx.alloc().buffer();
                        send(byteBuf, "Hello, world");
                        send(byteBuf, "Hi!");
                        ctx.writeAndFlush(byteBuf);
                        }
                    });
                }
            });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            log.error("client error......", e);
        } finally {
            worker.shutdownGracefully();
        }
    }

    private static void send(ByteBuf byteBuf, String content) {
        byte[] bytes = content.getBytes(); // 实际内容
        int length = bytes.length; // 实际内容长度
        byteBuf.writeInt(length);
        byteBuf.writeBytes(bytes);
    }

}
