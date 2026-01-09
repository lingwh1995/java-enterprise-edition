package org.bluebridge._03_eventloop_group_enhance._02_reuse_event_loop_group;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lingwh
 * @desc  创建多个Server时复用EventLoopGroup 客户端
 * @date 2025/9/23 11:58
 */
@Slf4j
public class EventLoopGroupReuseEventLoopGroupClient {

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

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroupReuseEventLoopGroupClient eventLoopGroupReuseEventLoopGroupClient = new EventLoopGroupReuseEventLoopGroupClient();
        // 启动第一个客户端
        eventLoopGroupReuseEventLoopGroupClient.startFirstClient();
//        // 启动第二个客户端
//        eventLoopGroupReuseEventLoopGroupClient.startSecondClient();
//        // 启动第三个客户端
//        eventLoopGroupReuseEventLoopGroupClient.startThirdClient();
    }

    /**
     * 启动第一个Netty客户端
     */
    public void startFirstClient() throws InterruptedException {
        // 创建EventLoopGroup实例并保持引用
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Channel channel = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 内部使用 CharBuffer.wrap(msg)
                        pipeline.addLast(new StringEncoder());
                    }
                })
                .connect(HOST, PORT_FIRST)
                .sync()
                .channel();
            channel.writeAndFlush("这是发给第一个服务器的数据......");
            
            // 等待连接关闭，确保数据发送完成
            channel.closeFuture().sync();
        } finally {
            // 关闭EventLoopGroup
            group.shutdownGracefully();
        }
    }

    /**
     * 启动第二个Netty客户端
     */
    public void startSecondClient() throws InterruptedException {
        // 创建EventLoopGroup实例并保持引用
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Channel channel = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 内部使用 CharBuffer.wrap(msg)
                        pipeline.addLast(new StringEncoder());
                    }
                })
                .connect(HOST, PORT_SECOND)
                .sync()
                .channel();
            channel.writeAndFlush("这是发给第二个服务器的数据......");
            
            // 等待连接关闭，确保数据发送完成
            channel.closeFuture().sync();
        } finally {
            // 关闭EventLoopGroup
            group.shutdownGracefully();
        }
    }

    /**
     * 启动第三个Netty客户端
     */
    public void startThirdClient() throws InterruptedException {
        // 创建EventLoopGroup实例并保持引用
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Channel channel = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 内部使用 CharBuffer.wrap(msg)
                        pipeline.addLast(new StringEncoder());
                    }
                })
                .connect(HOST, PORT_THIRD)
                .sync()
                .channel();
            channel.writeAndFlush("这是发给第三个服务器的数据......");
            
            // 等待连接关闭，确保数据发送完成
            channel.closeFuture().sync();
        } finally {
            // 关闭EventLoopGroup
            group.shutdownGracefully();
        }
    }

}
