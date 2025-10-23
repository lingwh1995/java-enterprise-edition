package org.bluebridge._06_channel_handler_lifecycle;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author lingwh
 * @desc ChannelHandler 生命周期
 * @date 2025/10/16 11:57
 */
@Slf4j
public class ChannelHandlerLifeCycleServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        new ServerBootstrap()
            .group(new NioEventLoopGroup())
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                    pipeline.addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            ByteBuf buf = (ByteBuf) msg;
                            String s = buf.toString(Charset.defaultCharset());
                            log.info("来自客户端的消息: {}", s);

                            // 创建新的ByteBuf并写入数据
                            ByteBuf responseBuf = ctx.alloc().buffer();
                            responseBuf.writeBytes(s.getBytes(Charset.defaultCharset()));
                            ctx.writeAndFlush(responseBuf);

                            // 释放原始ByteBuf资源
                            buf.release();
                        }

                        //生命周期
                        @Override
                        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                            log.info("channel注册......");
                        }

                        @Override
                        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                            log.info("channel移除......");
                        }

                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            log.info("channel活跃......");
                        }

                        @Override
                        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                            log.info("channel不活跃......");
                        }

                        @Override
                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                            log.info("用户事件触发......");
                        }

                        @Override
                        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
                            log.info("channel可写更改......");
                        }

                        @Override
                        public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception {
                            System.out.println("捕获到异常......");
                        }

                        @Override
                        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                            System.out.println("助手类添加......");
                        }

                        @Override
                        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
                            System.out.println("助手类移除......");
                        }
                    });
                }
            })
            .bind(HOST, PORT);
    }

}
