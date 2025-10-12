package org.bluebridge._04_channel;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @author lingwh
 * @desc 用来正确处理Channel的关闭工作 客户端
 * @date 2025/9/23 18:10
 */
@Slf4j
public class ChannelFutureCloseFutureClient {
    /*
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline是由多个ChannelHander组成的 pipeline = ch.pipeline();
                        pipeline.addLast(new StringEncoder());// 内部使用CharBuffer.wrap(msg)
                    }
                })
                // 连接到服务器
                // connect是一个异步非阻塞方法（即发起调用的main线程不阻塞，把任务交给connect线程）
                .connect("localhost", 8080);

        // 获取关闭后的channel
        Channel channel = channelFuture.sync().channel();
        ChannelFuture closeFuture = channel.closeFuture();
        closeFuture.sync();// 阻塞
        // 执行关闭后的操作
        group.shutdownGracefully(); // 先拒绝接受新的任务，把现有的任务能运行玩的运行完，然后再停止
    }*/

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override // 在连接建立后被调用
                    protected void initChannel(NioSocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                        pipeline.addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("localhost", 8080));

        Channel channel = channelFuture.sync().channel();
        log.info("{}", channel);

        new Thread(()->{
            Scanner scanner = new Scanner(System.in);
            while (true) {
                log.info("请输入要发送给服务端的信息：");
                String line = scanner.nextLine();
                if ("q".equals(line)) {
                    channel.close(); // close 异步操作 1s 之后
//                    log.info("处理关闭之后的操作"); // 不能在这里善后
                    break;
                }
                channel.writeAndFlush(line);
            }
        }, "input").start();

        // 获取 CloseFuture 对象， 1) 同步处理关闭， 2) 异步处理关闭
        ChannelFuture closeFuture = channel.closeFuture();

        // 同步处理关闭
        /*
        log.info("waiting close......");
        closeFuture.sync();
        log.info("处理关闭之后的操作");
        */

        // 异步处理关闭
        closeFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                log.info("处理关闭之后的操作......");
                group.shutdownGracefully(); // 先拒绝接受新的任务，把现有的任务能运行玩的运行完，然后再停止
            }
        });
    }

}
