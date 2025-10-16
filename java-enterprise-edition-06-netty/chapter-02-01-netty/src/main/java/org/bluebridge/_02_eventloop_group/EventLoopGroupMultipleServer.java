package org.bluebridge._02_eventloop_group;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author lingwh
 * @desc 多事件循环组 服务端
 * @date 2025/9/26 10:14
 */

/**
 * 双参数版本 => public ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup)
 * 1.允许分别指定父级和子级EventLoopGroup提供更灵活的线程模型配置
 *   parentGroup: 负责接收新连接(accept操作)，childGroup: 负责处理已建立连接的I/O操作
 * 2.适用于需要精细控制线程资源分配的高性能应用，可以为accept操作和I/O操作分配不同的线程池
 * 3.parentGroup相当于boss线程，childGroup处理worker线程
 */

@Slf4j
public class EventLoopGroupMultipleServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) throws InterruptedException {
        // 主线程池boss：用于接受客户端的请求链接，不做任何处理
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        // 从线程池worker：主线程池会把任务交给它，让其做任务
        NioEventLoopGroup worker = new NioEventLoopGroup(2);
        new ServerBootstrap()
            // 设置主从线程组
            .group(boss, worker)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                    pipeline.addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) {
                            ByteBuf buf = (ByteBuf) msg;
                            String s = buf.toString(Charset.defaultCharset());
                            // 在这里打印线程名称，可以看到两个NioEventLoopGroup在轮询处理来自3个客户端的连接
                            log.info("NioEventLoopGroup 名称：{}，接收到的字符串： {}", Thread.currentThread().getName(), s);
                        }
                    });
                }
            })
            .bind(HOST, PORT)
            .sync();
    }

}
