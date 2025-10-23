package org.bluebridge._05_channel_handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lingwh
 * @desc ChannelHandler 的具体实现 SimpleChannelInboundHandler 客户端
 * @date 2025/9/23 11:58
 */
@Slf4j
public class SimpleChannelInboundHandlerObjectClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) throws InterruptedException {
        Channel channel = new Bootstrap()
            .group(new NioEventLoopGroup())
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();
                    // 添加对象编码器
                    pipeline.addLast(new ObjectEncoder());
                }
            })
            .connect(HOST, PORT)
            .sync()
            .channel();

        // 发送实体对象
        Student student = new Student("张三");
        channel.writeAndFlush(student);
    }

}
