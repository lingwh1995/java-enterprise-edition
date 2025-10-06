package org.bluebridge._07_pipeline;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author lingwh
 * @desc Netty pipeline 服务端
 * @date 2025/9/24 15:30
 */

/**
 * pipeline.writeAndFlush(): 从最后一个Handler开始找出站处理器
 * ctx.writeAndFlush(): 从当前Handler开始找出站处理器
 */
@Slf4j
public class PipelineServer {

    public static void main(String[] args) {
        new ServerBootstrap()
            .group(new NioEventLoopGroup())
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                    // 1. 通过channel拿到pipeline
                    ChannelPipeline pipeline = nioSocketChannel.pipeline();

                    // 2. 添加Handler: head -> h1 -> h2 -> h3 -> tail
                    pipeline.addLast("h1", new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            log.info("h1......");
                            ByteBuf byteBuf = (ByteBuf) msg;
                            String name = byteBuf.toString(Charset.defaultCharset());
                            log.info("原始数据: {}", name);
                            // 内部执行ctx.fireChannelRead(name); 传递且只能传递给下一个handler, 如果不调用, 那么调用链就会断开
                            super.channelRead(ctx, name);
                        }
                    });

                    pipeline.addLast("h2", new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            log.info("h2......");
                            String name = msg.toString();
                            log.info("name: {}", name);
                            Student student = new Student(name);
                            super.channelRead(ctx, student);
                        }
                    });

                    pipeline.addLast("h3", new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            log.info("h3......");
                            Student student = (Student) msg;
                            log.info("student: {}", student);
//                                super.channelRead(ctx, msg);// 后面没有了, 所以这个方法没用

                            // 写一些数据，触发出站处理器
                            // 使用pipeline则从最后开始找出站处理器h6->h5->h4
                            //pipeline.writeAndFlush(ctx.alloc().buffer().writeBytes("Server".getBytes()));

                            // 使用nioSocketChannel则从最后开始找出站处理器h6->h5->h4
                            nioSocketChannel.writeAndFlush(ctx.alloc().buffer().writeBytes("Server".getBytes()));

                            // 使用cxt从当前处理器往前找出站处理器，即从h3往前找，那么就找不到h4,h5,h6
                            //ctx.writeAndFlush(ctx.alloc().buffer().writeBytes("Server".getBytes()));
                        }
                    });

                    // 增加出站处理器(建议使用addFirst，不易弄混) ---> 只有写数据的时候才会触发write方法
                    // 出站顺序: 6->5->4
                    pipeline.addLast("h4", new ChannelOutboundHandlerAdapter() {
                        @Override
                        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                            log.info("h4......");
                            super.write(ctx, msg, promise);
                        }
                    });

                    pipeline.addLast("h5", new ChannelOutboundHandlerAdapter() {
                        @Override
                        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                            log.info("h5......");
                            super.write(ctx, msg, promise);
                        }
                    });

                    pipeline.addLast("h6", new ChannelOutboundHandlerAdapter() {
                        @Override
                        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                            log.info("h6......");
                            super.write(ctx, msg, promise);
                        }
                    });
                }
            })
            .bind(8080);
    }

}

@AllArgsConstructor
@Data
class Student {
    private String name;
}