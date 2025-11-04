package org.bluebridge.server;

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
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @author lingwh
 * @desc
 * @date 2025/10/21 16:28
 */
@Slf4j
@Component
public class NettyWebSocketServer {

    @Value("${netty.websocket.host}")
    private String host;

    @Value("${netty.websocket.port}")
    private int port;

    @Value("${netty.websocket.ws-url}")
    private String wsUrl;

    @Resource
    private NettyWebSocketServerHandler nettyWebSocketServerHandler;

    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;

    @PostConstruct
    public void startNettyServer() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

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
                    pipeline.addLast(new WebSocketServerProtocolHandler(wsUrl));
                    // 添加自定义处理器
                    pipeline.addLast(nettyWebSocketServerHandler);
                }
            });

        try {
            ChannelFuture future = bootstrap.bind(host, port).sync();
            log.info("Netty WebSocket服务器启动成功，端口: {}", port);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
