package org.bluebridge._18_resolve_sticky_packet_and_half_packet._04_length_field_based_frame_decoder._03_production_demo;

import cn.hutool.core.util.HexUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 * @author lingwh
 * @desc 预设长度解码器解决黏包半包问题 - 案例一服务端（生产级别的示例）
 * @date 2025/10/14 11:47
 */
@Slf4j
public class LengthFieldBasedFrameDecoderServer {

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
                        // 预设长度解码器
                        /**
                         * 针对lengthAdjustment参数的说明: 是报文长度计算的补偿修正参数，本质是用来消除「报文中预定义的长度字段值」与「核心数据域的真实长度」之间的偏差，确保能够精准计算或截取有效报文数据
                         *    长度偏移 + 长度占用字节 + lengthAdjustment + 长度占用字节计算出来的长度 = 报文真实长度
                         *    lengthAdjustment = 报文真实长度 - 长度偏移 - 长度占用字节 - 长度占用字节计算出来的长度
                         *
                         *    示例报文 -> 680006ABCD16
                         *    68    帧头
                         *    0006 长度字段占用字节数
                         *    ABCD 数据域
                         *    16   帧尾
                         *    1+2+6
                         *    lengthAdjustment = 6 - 1 - 2 - 6 = -3
                         */

                        /**
                         * 针对failFast参数的说明
                         * failFast=true：
                         *    时机：读取完长度字段后立即校验，超标则马上抛出 TooLongFrameException。
                         *    行为：不读取超大帧的剩余数据。
                         *    特点：节省资源、快速阻断无效数据，生产环境默认推荐。
                         * failFast=false：
                         *    时机：先完整读取整个超大帧的所有数据，再校验并抛出 TooLongFrameException。
                         *    行为：保留完整的超大帧数据。
                         *    特点：消耗更多 IO / 内存资源，仅用于调试排障、审计留存等特殊场景。
                         * 总结
                         *    无特殊情况直接选true
                         */
                        // 大小端，最大长度，长度偏移，长度占用字节，长度调整，剥离字节数，超大帧异常的抛出时机
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN, 1024, 1, 2, -3, 0, true));

                        // 特别注意：这里的LoggingHandler不能放在第一个位，否则打印出来的数据是原始数据，不是解码器处理后的数据，很有可能有黏包半包情况
                        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));

                        pipeline.addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                // 打印整个 pipeline 的结构，即处理器调用链路
                                log.info("pipeline {}", ctx.pipeline());
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
                                log.info("received {}", msg);
                                // 1. 获取 ByteBuf
                                ByteBuf byteBuf = (ByteBuf) msg;

                                // 2. ByteBuf 转为字节数组（只读不移动索引，不影响后续对 ByteBuf 的操作）
                                byte[] bytes = new byte[byteBuf.readableBytes()];
                                byteBuf.getBytes(byteBuf.readerIndex(), bytes);

                                // 3.转为hex
                                String hex = HexUtil.encodeHexStr(bytes);
                                log.info("received {}", hex);

                                // 4.校验数据
                                if(hex.startsWith("68") && hex.endsWith("16")) {
                                    log.info("服务端接收到的报文成功的处理了黏包和半包......");
                                }
                            }
{                            }
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
