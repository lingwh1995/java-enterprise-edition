package org.bluebridge._05_channel;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lingwh
 * @desc 测试ChannelFuture 客户单
 * @date 2025/9/26 14:53
 */

/**
 * 让异步方法同步的两种方式：
 * 1. 调用 sync 方法，阻塞等待异步操作完成
 * 2. 调用 addListener 方法，添加一个监听器，异步操作完成时会调用监听器
 */
@Slf4j(topic = "·")
public class ChannelFutureClient {

    public static void main(String[] args) throws InterruptedException {
        //makeChannelFutureSyncUseSync();
        makeChannelFutureSyncUseAddListener();
    }

    /**
     * 调用sync()让ChannelFuture异步变同步
     * @throws InterruptedException
     */
    private static void makeChannelFutureSyncUseSync() throws InterruptedException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect("127.0.0.1", 8080);
        log.info("连接成功");

        /**
         * 执行到 1 时，连接未建立，打印 [id: 0x2e1884dd]
         * 执行到 2 时，sync 方法是同步等待连接建立完成
         * 执行到 3 时，连接肯定建立了，打印 [id: 0x2e1884dd, L:/127.0.0.1:57191 - R:/127.0.0.1:8080]
         */
        log.info("{}", channelFuture.channel()); // 1
        channelFuture.sync(); // 2
        log.info("{}", channelFuture.channel()); // 3
    }

    /**
     * 调用 addListener() 让ChannelFuture异步变同步
     * @throws InterruptedException
     */
    private static void makeChannelFutureSyncUseAddListener() {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect("127.0.0.1", 8080);

        /**
         * 执行到 1 时，连接未建立，打印 [id: 0x749124ba]
         * ChannelFutureListener 会在连接建立时被调用（其中 operationComplete 方法），因此执行到 2 时，连接肯定建立了，打印 [id: 0x749124ba, L:/127.0.0.1:57351 - R:/127.0.0.1:8080]
         */
        log.info("{}", channelFuture.channel()); // 1
        channelFuture.addListener((ChannelFutureListener) future -> {
            log.info("{}", channelFuture.channel()); // 2
        });
    }

}
