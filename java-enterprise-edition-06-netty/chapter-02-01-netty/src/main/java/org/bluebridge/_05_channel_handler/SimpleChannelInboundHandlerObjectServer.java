package org.bluebridge._05_channel_handler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import lombok.extern.slf4j.Slf4j;


/**
 * @author lingwh
 * @desc ChannelHandler的具体实现SimpleChannelInboundHandler 服务端
 * @date 2025/10/10 11:32
 */

/**
 * SimpleChannelInboundHandler
 *    1.SimpleChannelInboundHandler是ChannelInboundHandlerAdapter的子类，可以重写父类的所有方法
 *    2.SimpleChannelInboundHandler可以使用泛型来指定接收的消息类型，这样的好处是不用手动强制类型转换
 *    3.当SimpleChannelInboundHandler接收到消息时，会自动调用channelRead0方法，将消息作为参数传递给该方法
 *
 * 本程序演示了使用 Student 来作为SimpleChannelInboundHandler
 */
@Slf4j
public class SimpleChannelInboundHandlerObjectServer {

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
                    // 添加对象解码器
                    pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                    // 接收实体对象
                    pipeline.addLast(new SimpleChannelInboundHandler<Student>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Student student) {
                            log.info("student: {}", student);
                        }
                    });
                }
            })
            .bind(HOST, PORT);
    }

}
