package org.bluebridge._05_channel_handler;

import cn.hutool.json.JSONUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;


/**
 * @author lingwh
 * @desc ChannelHandler 的具体实现 SimpleChannelInboundHandler 服务端
 * @date 2025/10/10 11:32
 */

/**
 * SimpleChannelInboundHandler
 *    1. SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter 的子类，可以重写父类的所有方法
 *    2. SimpleChannelInboundHandler 可以使用泛型来指定接收的消息类型，这样的好处是不用手动强制类型转换
 *    3. 当SimpleChannelInboundHandler 接收到消息时，会自动调用 channelRead0() 方法，将消息作为参数传递给该方法
 *
 * 本程序演示了使用 String 来作为 SimpleChannelInboundHandler，一般来使用 String 类型，然后再对字符串进行处理
 */
@Slf4j
public class SimpleChannelInboundHandlerStringServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        new ServerBootstrap()
            .group(new NioEventLoopGroup())
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new StringDecoder());
                    // 接收字符串
                    pipeline.addLast(new SimpleChannelInboundHandler<String>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) {
                            Student student = JSONUtil.toBean(msg, Student.class);
                            log.info("student: {}", student);
                        }
                    });
                }
            })
            .bind(HOST, PORT);
    }

}
