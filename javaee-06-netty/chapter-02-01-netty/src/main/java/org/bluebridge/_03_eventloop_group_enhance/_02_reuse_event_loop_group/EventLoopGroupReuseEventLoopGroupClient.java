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
 * @desc  创建多个Client时复用EventLoopGroup 客户端（优化版）
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

    /**
     * 全局共享的EventLoopGroup（所有客户端复用），手动指定4个线程，优化资源占用
     */
    private final NioEventLoopGroup sharedEventLoopGroup = new NioEventLoopGroup(4);

    public static void main(String[] args) {
        EventLoopGroupReuseEventLoopGroupClient client = new EventLoopGroupReuseEventLoopGroupClient();
        // 并发启动三个客户端（无阻塞，主线程快速执行）
        client.startClient(HOST, PORT_FIRST, "这是第一个客户端发给第一个服务器的数据......");
        client.startClient(HOST, PORT_SECOND, "这是第二个客户端发给第二个服务器的数据......");
        client.startClient(HOST, PORT_THIRD, "这是第三个客户端发给第三个服务器的数据......");

        // 应用退出时，统一关闭共享的EventLoopGroup（此处仅做演示，实际可通过钩子函数关闭）
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("开始优雅关闭共享EventLoopGroup......");
            client.sharedEventLoopGroup.shutdownGracefully();
            log.info("共享EventLoopGroup已关闭......");
        }));
    }

    /**
     * 启动客户端
     * @param host
     * @param port
     * @param msg
     */
    public void startClient(String host, Integer port, String msg) {
        try {
            Channel channel = new Bootstrap()
                // 复用全局共享的EventLoopGroup
                .group(sharedEventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 内部使用 CharBuffer.wrap(msg)
                        pipeline.addLast(new StringEncoder());
                    }
                })
                .connect(host, port)
                .sync()
                .channel();

            channel.writeAndFlush(msg).addListener(writeFuture -> {
                if (writeFuture.isSuccess()) {
                    log.info("客户端连接端口 {} 成功，数据发送完成：{}", port, msg);
                } else {
                    log.error("客户端连接端口 {} 成功，但数据发送失败!", port, writeFuture.cause());
                }
            });

            // 异步监听连接关闭事件，不阻塞主线程
            channel.closeFuture().addListener(closeFuture -> {
                log.info("客户端连接端口 {} 已关闭", port);
            });
        } catch (Exception e) {
            log.error("客户端启动异常（端口：{}）", port, e);
        }
    }
}
