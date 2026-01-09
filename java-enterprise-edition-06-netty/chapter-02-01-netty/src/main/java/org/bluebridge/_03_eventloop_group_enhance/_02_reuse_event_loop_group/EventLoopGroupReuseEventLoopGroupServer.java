package org.bluebridge._03_eventloop_group_enhance._02_reuse_event_loop_group;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lingwh
 * @desc 使用独立的 EventLoopGroup 创建多个Server时复用EventLoopGroup 服务端
 * @date 2025/9/23 13:47
 */
@Slf4j
public class EventLoopGroupReuseEventLoopGroupServer {

    /**
     * 全局线程池 - bossGroup
     */
    private final EventLoopGroup sharedBossGroup = new NioEventLoopGroup();

    /**
     * 全局线程池 - workerGroup
     */
    private final EventLoopGroup sharedWorkerGroup = new NioEventLoopGroup();

    /**
     * 服务器的IP地址
     */
    private static final String HOST = "127.0.0.1";

    /**
     * 第一个服务器的端口
     */
    private static final Integer PORT_FIRST = 8001;

    /**
     * 第二个服务器的端口
     */
    private static final Integer PORT_SECOND = 8002;

    /**
     * 第三个服务器的端口
     */
    private static final Integer PORT_THIRD = 8003;

    public static void main(String[] args) {
        EventLoopGroupReuseEventLoopGroupServer server = new EventLoopGroupReuseEventLoopGroupServer();
        // 启动第一个服务器
        server.startServer(HOST, PORT_FIRST);
        // 启动第二个服务器
        server.startServer(HOST, PORT_SECOND);
        // 启动第三个服务器
        server.startServer(HOST, PORT_THIRD);
    }

    /**
     * 启动服务端
     * @param host
     * @param port
     */
    public void startServer(String host, Integer port) {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 直接复用全局的bossGroup和workerGroup
            serverBootstrap.group(sharedBossGroup, sharedWorkerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 512)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 配置当前服务器的ChannelPipeline
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                                log.info("服务端收到数据：{}", msg);
                            }
                        });
                    }
                });
            ChannelFuture future = serverBootstrap.bind(host, port).sync();
            log.info("TCP服务器 {}:{} 启动成功......", host, port);
            future.channel().closeFuture().addListener(f -> log.info("TCP服务器 {}:{} 关闭成功......", host, port));
        } catch (Exception e) {
            log.error("TCP服务器 {}:{} 启动失败......", host, port);
        }
    }

    // 应用关闭时：统一关闭线程池，释放所有资源
    public void shutdown() {
        sharedBossGroup.shutdownGracefully();
        sharedWorkerGroup.shutdownGracefully();
        log.info("Netty线程池已优雅关闭......");
    }

}

