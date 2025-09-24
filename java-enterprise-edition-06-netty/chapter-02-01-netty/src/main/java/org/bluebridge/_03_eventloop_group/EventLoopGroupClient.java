package org.bluebridge._03_eventloop_group;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lingwh
 * @desc 事件循环Client端
 * @date 2025/9/23 11:58
 */
@Slf4j(topic = "·")
public class EventLoopGroupClient {

    public static void main(String[] args) throws InterruptedException {
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
//          .writeAndFlush("我是nio");
        channel.writeAndFlush("我是nio");
        // 使用debug模式向服务器发送数据
//        System.out.println(channel);
    }

}
