package org.bluebridge._03_eventloop_group_enhance;

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
 * @desc 使用独立的 EventLoopGroup 处理耗时较长的任务客户端
 * @date 2025/9/23 11:58
 */
@Slf4j
public class EventLoopGroupEnhanceClient {

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
                    // 内部使用 CharBuffer.wrap(msg)
                    pipeline.addLast(new StringEncoder());
                }
            })
            .connect(HOST, PORT)
            .sync()
            .channel();
        channel.writeAndFlush("zhangsan");
//        TimeUnit.MILLISECONDS.sleep(2000);
//        channel.writeAndFlush("zhangsan");
    }

}
