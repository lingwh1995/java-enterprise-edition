package org.bluebridge._17_resolve_sticky_packet_and_half_packet._02_fixed_length_frame_decoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Random;

/**
 * @author lingwh
 * @desc 固定长度解码器解决黏包半包问题 客户端
 * @date 2025/10/11 10:43
 */
@Slf4j
public class FixedLengthFrameDecoderClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                .channel(NioSocketChannel.class)
                .group(worker)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        log.info("connected......");
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                        pipeline.addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) {
                                ByteBuf buf = ctx.alloc().buffer();
                                char c = '0';
                                Random r = new Random();
                                for (int i = 0; i < 10; i++) {
                                    byte[] bytes = fill10Bytes(c, r.nextInt(10) + 1);
                                    c++;
                                    buf.writeBytes(bytes);
                                }
                                ctx.writeAndFlush(buf);
                            }
                        });
                    }
                });
            ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            log.error("client error......", e);
        } finally {
            worker.shutdownGracefully();
        }
    }

    public static byte[] fill10Bytes(char c, int len) {
        byte[] bytes = new byte[10];
        Arrays.fill(bytes, (byte) '_');
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) c;
        }
        System.out.println(new String(bytes));
        return bytes;
    }

}