package org.bluebridge._23_udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author lingwh
 * @desc Netty 客户端UDP版
 * @date 2025/11/12 16:43
 */
@Slf4j
public class NettyClient {

    // 服务端地址和端口
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(NioDatagramChannel.class)
                .handler(new ChannelInitializer<DatagramChannel>() {
                    @Override
                    protected void initChannel(DatagramChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                        pipeline.addLast(new UdpClientHandler());
                    }
                });

            Channel channel = bootstrap.bind(0).sync().channel();
            log.info("UDP 客户端已启动，准备发送数据......");

            // 发送数据报
            channel.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer("Hello UDP Server", CharsetUtil.UTF_8),
                    new InetSocketAddress(HOST, PORT)
            )).sync();

            // 等待2秒，给服务器响应时间
            Thread.sleep(2000);

            // 发送第二条消息
            channel.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer("This is second message", CharsetUtil.UTF_8),
                    new InetSocketAddress(HOST, PORT)
            )).sync();

            // 等待2秒，接收响应后再退出
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

}
