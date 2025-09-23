package org.bluebridge._02_eventloop_group;

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

/**
 * @author lingwh
 * @desc
 * @date 2025/9/23 13:47
 */
@Slf4j(topic = "·")
public class EventLoopGroupServer {

    public static void main(String[] args) {
        // 细分: 独立出来一个EventLoopGroup来处理耗时较长的任务
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        /**
         * 注意到两次输出的thread名不一样(nioEventLoopGroup-4-1， nioEventLoopGroup-2-1 )， 说明提交给不同的Group执行， 其中nioEventLoopGroup-4-1的4指的是第4个Group， 1为当前Group的线程号
         */
        new ServerBootstrap()
            .group(new NioEventLoopGroup(),new NioEventLoopGroup(2))// (parentGroup, childGroup)--->(负责Accept事件, 负责I/O事件)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                    nioSocketChannel.pipeline()
                    .addLast("handler1",new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            ByteBuf buffer = (ByteBuf) msg;
                            String s = buffer.toString(Charset.defaultCharset());
                            log.debug("第一个handler中接收到的字符串： {}", s);
                            // 让消息传递给下一个handler
                            ctx.fireChannelRead(msg);
                        }
                    }).addLast(eventExecutors, "handler2", new ChannelInboundHandlerAdapter() { // 传入指定的eventGroup
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            ByteBuf buffer = (ByteBuf) msg;
                            String s = buffer.toString(Charset.defaultCharset());
                            log.debug("第二个handler中接收到的字符串： {}", s);
                        }
                    });
                }
            })
            .bind(8080);
    }

}
