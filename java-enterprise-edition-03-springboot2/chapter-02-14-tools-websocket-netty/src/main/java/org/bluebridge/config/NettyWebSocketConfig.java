package org.bluebridge.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author lingwh
 * @desc
 * @date 2025/10/21 16:28
 */
@Slf4j
@Configuration
public class NettyWebSocketConfig {

    @Value("${netty.websocket.port}")
    private int port;

    @PostConstruct
    public void startNettyServer() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        bootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    // 添加HTTP编解码器
                    pipeline.addLast(new HttpServerCodec());
                    // 添加日志处理器
                    pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                    // 添加聚合器
                    pipeline.addLast(new HttpObjectAggregator(65536));
                    // 添加WebSocket支持
                    pipeline.addLast(new WebSocketServerProtocolHandler("ws://localhost:8088/websocket"));
                    // 添加自定义处理器
                    pipeline.addLast(nettyWebSocketServerHandler());
                }
            });

        try {
            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("Netty WebSocket服务器启动成功，端口: {}", port);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public NettyWebSocketServerHandler nettyWebSocketServerHandler() {
        return new NettyWebSocketServerHandler();
    }

}
