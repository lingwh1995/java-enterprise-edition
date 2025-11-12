package org.bluebridge._23_udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;


/**
 * @author lingwh
 * @desc Netty 服务端UDP版
 * @date 2025/11/12 16:42
 */
@Slf4j
public class NettyServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(NioDatagramChannel.class)
                // 设置 UDP 广播
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<DatagramChannel>() {
                    @Override
                    protected void initChannel(DatagramChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                        pipeline.addLast(new UdpServerHandler());
                    }
                });

            Channel channel = bootstrap.bind(HOST, PORT).sync().channel();
            log.info("UDP 服务端已启动，监听端口: {}", PORT);
            // 保持服务端运行，等待关闭
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("UDP 服务端异常", e);
        } finally {
            group.shutdownGracefully();
        }
    }

}
