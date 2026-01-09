package org.bluebridge._18_resolve_sticky_packet_and_half_packet._04_length_field_based_frame_decoder._03_production_demo;

import cn.hutool.core.util.HexUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * @author lingwh
 * @desc 预设长度解码器解决黏包半包问题 - 案例一客户端（生产级别的示例）
 * @date 2025/10/14 11:48
 */
@Slf4j
public class LengthFieldBasedFrameDecoderClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

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
                    pipeline.addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            ByteBuf buffer = ctx.alloc().buffer();
                            // 1. 定义两包独立的数据
                            // 黏包测试数据
                            String stickyPacketHex = "68340001508000340100008320122106150000000901261800340100008320122106328512C225107508868900B10468CF01DC1668340001508000340100008320";
                            // 半包测试数据
                            String halfPacketHex = "122106150000000901261800340100008320122106328512C225107508868900B10468CF01DC16";

                            // 2. 组装第一包数据（产生黏包现象的数据）用的ByteBuf
                            ByteBuf spByteBuf = ctx.alloc().buffer();
                            spByteBuf.writeBytes(HexUtil.decodeHex(stickyPacketHex));
                            // 发送第一包数据并刷新
                            ctx.writeAndFlush(spByteBuf);

                            // 3. 组装第二包数据（产生半包现象的数据）用的ByteBuf
                            ByteBuf hpByteBuf = ctx.alloc().buffer();
                            hpByteBuf.writeBytes(HexUtil.decodeHex(halfPacketHex));
                            // 发送第二包数据并刷新
                            ctx.writeAndFlush(hpByteBuf);
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

}
