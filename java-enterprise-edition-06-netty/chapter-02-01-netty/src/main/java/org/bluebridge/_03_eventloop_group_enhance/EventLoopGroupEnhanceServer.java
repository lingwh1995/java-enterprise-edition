package org.bluebridge._03_eventloop_group_enhance;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * @author lingwh
 * @desc 使用独立的EventLoopGroup处理耗时较长的任务 服务端
 * @date 2025/9/23 13:47
 */
@Slf4j
public class EventLoopGroupEnhanceServer {

    public static void main(String[] args) {
        // 细分: 独立出来一个EventLoopGroup来处理耗时较长的任务
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        /**
         * 注意到两次输出的thread名不一样(nioEventLoopGroup-4-1， nioEventLoopGroup-2-1 )， 说明提交给不同的Group执行， 其中nioEventLoopGroup-4-1的4指的是第4个Group， 1为当前Group的线程号
         */
        new ServerBootstrap()
            .group(new NioEventLoopGroup(1),new NioEventLoopGroup(2))// (parentGroup, childGroup)--->(负责Accept事件, 负责I/O事件)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel nioSocketChannel) {
                    nioSocketChannel.pipeline()
                    .addLast("handler1",new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) {
                            ByteBuf buf = (ByteBuf) msg;
                            String s = buf.toString(Charset.defaultCharset());
                            log.debug("NioEventLoopGroup 名称：{}，第一个handler开始处理耗时任务： {}", Thread.currentThread().getName(), s);
                            // 让消息传递给下一个handler
                            ctx.fireChannelRead(msg);
                        }
                    }).addLast(eventExecutors, "handler2", new ChannelInboundHandlerAdapter() { // 传入指定的eventGroup
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) {
                            ByteBuf buf = (ByteBuf) msg;
                            String s = buf.toString(Charset.defaultCharset());
                            // 使用异步方式处理耗时任务，避免阻塞I/O线程
                            eventExecutors.submit(() -> {
                                log.debug("NioEventLoopGroup 名称：{}，第二个handler开始处理耗时任务，内容：{}", Thread.currentThread().getName(), s);

                                // 模拟耗时任务（例如数据库查询、复杂计算等）
                                try {
                                    TimeUnit.MILLISECONDS.sleep(5000); // 暂停5秒，模拟实际业务中的耗时操作
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }

                                log.debug("NioEventLoopGroup 名称：{}，第二个handler耗时任务处理完成", Thread.currentThread().getName());
                            });
                            // 让消息传递给下一个handler
                            ctx.fireChannelRead(msg);
                        }
                    }).addLast(eventExecutors, "handler3", new ChannelInboundHandlerAdapter() { // 传入指定的eventGroup
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) {
                            ByteBuf buf = (ByteBuf) msg;
                            String s = buf.toString(Charset.defaultCharset());
                            log.debug("NioEventLoopGroup 名称：{}，第三个handler开始处理耗时任务： {}", Thread.currentThread().getName(), s);
                            buf.release(); // 释放ByteBuf，避免内存泄漏（可选但推荐）
                        }
                    });
                }
            })
            .bind(8080);
    }

}
