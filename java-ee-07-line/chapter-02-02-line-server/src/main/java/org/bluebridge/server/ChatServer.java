package org.bluebridge.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.server.initializer.ChannelInitializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @author lingwh
 * @desc 聊天服务器
 * @date 2025/10/25 12:17
 */

/**
 * 测试方法
 *  1.cmd -> telnet 127.0.0.1 8080/telnet localhost 8080 -> 直接输入内容（只能发送单个字符）/按下Ctrl+]后输入 send + 内容（可以发送字符串） -> 查看idea控制台接收到的信息
 *  2.启动多个客户端
 */
@Slf4j
@Component
public class ChatServer {

    @Value("${netty.tcp.host}")
    private String host;

    @Value("${netty.tcp.port}")
    private int port;

    @Resource
    private ChannelInitializer channelInitializer;

    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    @PostConstruct
    public void startNettyServer() {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(channelInitializer);
            Channel channel = serverBootstrap.bind(host, port).sync().channel();
            log.info("Line服务器端启动成功，监听地址：{}，端口：{}", host, port);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("Line服务器端启动发生异常......", e);
        }
    }

    @PreDestroy
    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
