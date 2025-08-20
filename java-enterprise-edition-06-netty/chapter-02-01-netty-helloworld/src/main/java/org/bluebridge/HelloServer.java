package org.bluebridge;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lingwh
 * @desc 服务端
 * @date 2025/8/20 17:24
 */
@Slf4j
public class HelloServer {

    public static void main(String[] args) {
        //1.ServerBootstrap: 负责组装netty组件,启动netty
        new ServerBootstrap()
            //2.EventLoop: WorkerEventLoop(selector,thread),类似于accept方法的作用
            .group(new NioEventLoopGroup())
            //3.选择netty使用的ServerSocket
            .channel(NioServerSocketChannel.class)
            //4.boss负责连接，worker(child)负责处理读写，决定了worker(child)能执行什么操作
            .childHandler(
                //5.代表和客户端进行数据读写的通道,负责添加别的handler,在initChannel()方法中进行添加
                new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //6.添加具体handler
                        ch.pipeline().addLast(new StringDecoder());//将ByteBuf转换为字符串
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){//自定义的handler
                            //读事件
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("msg: {}", msg);
                            }
                        });
                    }
                }
            ).bind(8080);
    }

}

