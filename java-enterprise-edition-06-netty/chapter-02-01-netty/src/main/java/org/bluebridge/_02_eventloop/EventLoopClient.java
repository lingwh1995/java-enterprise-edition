package org.bluebridge._02_eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author lingwh
 * @desc 事件循环Client端
 * @date 2025/9/23 11:58
 */
@Slf4j(topic = "·")
public class EventLoopClient {

    public static void main(String[] args) throws InterruptedException {
        // 模拟第一个客户端连接服务器
        Channel channel = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder());// 内部使用CharBuffer.wrap(msg)
                    }
                })
                .connect("localhost", 8080)
                .sync()
                .channel();
        log.info("channel:" + channel);
        channel.writeAndFlush("zhangsan");
        TimeUnit.MILLISECONDS.sleep(2000);
        channel.writeAndFlush("zhangsan");

        // 模拟第二个客户端连接服务器
        channel = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder());// 内部使用CharBuffer.wrap(msg)
                    }
                })
                .connect("localhost", 8080)
                .sync()
                .channel();
        log.info("channel:" + channel);
        channel.writeAndFlush("lisi");
        TimeUnit.MILLISECONDS.sleep(2000);
        channel.writeAndFlush("lisi");

        // 模拟第三个客户端连接服务器
        channel = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder());// 内部使用CharBuffer.wrap(msg)
                    }
                })
                .connect("localhost", 8080)
                .sync()
                .channel();
        log.info("channel:" + channel);
        channel.writeAndFlush("wangwu");
        TimeUnit.MILLISECONDS.sleep(2000);
        channel.writeAndFlush("wangwu");
    }

}
