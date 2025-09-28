package org.bluebridge._03_eventloop_group;

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
/**
 * 测试方法：
 *    客户端，启动三次，分别修改发送字符串为 zhangsan（第一次），lisi（第二次），wangwu（第三次），可
 *    以看到两个工人轮流处理 channel，但工人与 channel 之间进行了绑定
 */
@Slf4j(topic = "·")
public class EventLoopGroupClient {

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
