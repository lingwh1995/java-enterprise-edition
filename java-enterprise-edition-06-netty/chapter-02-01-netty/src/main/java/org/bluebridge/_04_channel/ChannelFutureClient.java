package org.bluebridge._04_channel;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lingwh
 * @desc 使用ChannelFuture正确处理连接建立 客户端
 * @date 2025/9/26 14:53
 */

/**
 * 让异步方法同步（获取建立好的连接）的两种方式：
 *   1. 调用 sync 方法，阻塞等待异步操作完成
 *   2. 调用 addListener 方法，添加一个监听器，异步操作完成时会调用监听器
 */
@Slf4j
public class ChannelFutureClient {

    public static void main(String[] args) throws InterruptedException {
        //makeChannelFutureSyncUseSync();
        makeChannelFutureSyncUseAddListener();
    }

    /**
     * 调用sync()让ChannelFuture异步变同步：等待执行结果的是主线程（注意观察打印日志使用的线程的名称）
     * @throws InterruptedException
     */
    private static void makeChannelFutureSyncUseSync() throws InterruptedException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringEncoder());
                    }
                })
                .connect("127.0.0.1", 8080);
        log.info("连接成功......");

        /**
         * 执行到 1 时，连接未建立，打印 [id: 0x2e1884dd]
         * 执行到 2 时，sync 方法是同步等待连接建立完成
         * 执行到 3 时，连接肯定建立了，打印 [id: 0x2e1884dd, L:/127.0.0.1:57191 - R:/127.0.0.1:8080]
         */
        log.info("当前线程：{}， channel：{}",
                Thread.currentThread().getName(), channelFuture.channel()); // 1
        channelFuture.sync(); // 2
        log.info("当前线程：{}， channel：{}",
                Thread.currentThread().getName(), channelFuture.channel()); // 3
    }

    /**
     * 调用 addListener() 让ChannelFuture异步变同步：等待执行结果的也是 NioEventLoopGroup 线程（注意观察打印日志使用的线程的名称）
     * @throws InterruptedException
     */
    private static void makeChannelFutureSyncUseAddListener() {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new StringEncoder());
                    }
                })
                .connect("127.0.0.1", 8080);

        /**
         * 执行到 1 时，连接未建立，打印 [id: 0x749124ba]
         * ChannelFutureListener 会在连接建立时被调用（其中 operationComplete 方法），因此执行到 2 时，连接肯定建立了，打印 [id: 0x749124ba, L:/127.0.0.1:57351 - R:/127.0.0.1:8080]
         */
        log.info("{}", channelFuture.channel()); // 1
        channelFuture.addListener((ChannelFutureListener) future -> {
            log.info("当前线程：{}， channel：{}",
                    Thread.currentThread().getName(), channelFuture.channel()); // 2
        });
    }

}
