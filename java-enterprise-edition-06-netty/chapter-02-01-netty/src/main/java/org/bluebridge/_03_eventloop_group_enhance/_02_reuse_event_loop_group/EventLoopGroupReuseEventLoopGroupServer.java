package org.bluebridge._03_eventloop_group_enhance._02_reuse_event_loop_group;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
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
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();

    /**
     * 全局线程池 - workerGroup
     */
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

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
        EventLoopGroupReuseEventLoopGroupServer eventLoopGroupReuseEventLoopGroupServer = new EventLoopGroupReuseEventLoopGroupServer();
        // 启动第一个服务器
        eventLoopGroupReuseEventLoopGroupServer.startFirstServer();
        // 启动第二个服务器
        eventLoopGroupReuseEventLoopGroupServer.startSecondServer();
        // 启动第三个服务器
        eventLoopGroupReuseEventLoopGroupServer.startThirdServer();
    }

    /**
     * 启动第一个Netty服务器
     */
    public void startFirstServer() {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 直接复用全局的bossGroup和workerGroup
            serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 512)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        // 配置当前服务器的ChannelPipeline
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2));
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                super.channelRead(ctx, msg);
                            }
                        });
                    }
                });
            ChannelFuture future = serverBootstrap.bind(HOST, PORT_FIRST).sync();
            log.info("TCP服务器1启动成功，端口 {}......", PORT_FIRST);
            future.channel().closeFuture().addListener(f -> log.info("TCP服务器1关闭......"));
        } catch (Exception e) {
            log.error("TCP服务器1启动失败......", e);
        }
    }

    /**
     * 启动第二个Netty服务器
     */
    public void startSecondServer() {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 复用同一对bossGroup和workerGroup
            serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 512)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        // 配置当前服务器的ChannelPipeline（可与服务器1不同）
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(2048, 1, 4, 0, 4));
                    }
                });
            ChannelFuture future = serverBootstrap.bind(HOST, PORT_SECOND).sync();
            log.info("TCP服务器2启动成功，端口 {}......", PORT_SECOND);
            future.channel().closeFuture().addListener(f -> log.info("TCP服务器2关闭......"));
        } catch (Exception e) {
            log.error("TCP服务器2启动失败......", e);
        }
    }

    /**
     * 启动第三个Netty服务器
     */
    public void startThirdServer() {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 复用同一对bossGroup和workerGroup
            serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 512)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        // 配置当前服务器的ChannelPipeline（可与服务器1不同）
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(2048, 1, 4, 0, 4));
                    }
                });
            ChannelFuture future = serverBootstrap.bind(HOST, PORT_THIRD).sync();
            log.info("TCP服务器3启动成功，端口 {}......", PORT_THIRD);
            future.channel().closeFuture().addListener(f -> log.info("TCP服务器3关闭......"));
        } catch (Exception e) {
            log.error("TCP服务器3启动失败......", e);
        }
    }

    // 应用关闭时：统一关闭线程池，释放所有资源
    public void shutdown() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        log.info("Netty线程池已优雅关闭......");
    }

}

