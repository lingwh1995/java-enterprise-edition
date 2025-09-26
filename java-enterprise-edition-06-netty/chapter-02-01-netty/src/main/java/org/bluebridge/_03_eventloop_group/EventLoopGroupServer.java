package org.bluebridge._03_eventloop_group;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author lingwh
 * @desc 事件循环组服务端
 * @date 2025/9/26 10:14
 */

/**
 * 3个客户端连接服务端，可以看到两个NioEventLoopGroup在交替处理三个连接
 */
@Slf4j(topic = "·")
public class EventLoopGroupServer {

    public static void main(String[] args) throws InterruptedException {
        new ServerBootstrap()
            .group(new NioEventLoopGroup(1), new NioEventLoopGroup(2)) // 服务器端两个nio worker工人
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) {
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) {
                            ByteBuf buf = (ByteBuf) msg;
                            String s = buf.toString(Charset.defaultCharset());
                            // 在这里打印线程名称，可以看到第一个NioEventLoopGroup和第二个NioEventLoopGroup在轮询处理来自客户端的连接
                            log.debug("NioEventLoopGroup 名称：{}，接收到的字符串： {}", Thread.currentThread().getName(), s);
                        }
                    });
                }
            })
            .bind(8080)
            .sync();
    }

}
