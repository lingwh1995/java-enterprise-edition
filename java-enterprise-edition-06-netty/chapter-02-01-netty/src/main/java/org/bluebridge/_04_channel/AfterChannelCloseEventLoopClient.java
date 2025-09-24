package org.bluebridge._04_channel;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lingwh
 * @desc 获取关闭后的Channel，用来执行善后工作
 * @date 2025/9/23 18:10
 */
@Slf4j(topic = "·")
public class AfterChannelCloseEventLoopClient {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder());// 内部使用CharBuffer.wrap(msg)
                    }
                })
                // 连接到服务器
                // connect是一个异步非阻塞方法(即发起调用的main线程不阻塞, 把任务交给connect线程)
                .connect("localhost", 8080);

        // 获取关闭后的channel
        Channel channel = channelFuture.sync().channel();
        ChannelFuture closeFuture = channel.closeFuture();
        closeFuture.sync();// 阻塞
        // 执行关闭后的操作
        group.shutdownGracefully();// 先拒绝接受新的任务， 把现有的任务能运行玩的运行完，然后再停止
    }

}
