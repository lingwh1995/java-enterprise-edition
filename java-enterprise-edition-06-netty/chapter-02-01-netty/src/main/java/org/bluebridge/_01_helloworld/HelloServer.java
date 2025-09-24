package org.bluebridge._01_helloworld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
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

/**
 * Netty中的概念理解
 *   1.channel 可以理解为数据的通道
 *   2.msg 可以理解为流动的数据，最开始输入是 ByteBuf，但经过 pipeline 中的各个 handler 加工，会变成其它类型对象，最后输出又变成 ByteBuf
 *   3.handler 和 pipeline
 *       handler可以理解为数据的处理工序，分为 Inbound 和 Outbound 两类，多道工序合在一起就是 pipeline（传递途径）
 *       pipeline 中有多个 handler，处理时会依次调用其中的 handler，pipeline 负责发布事件（读、读取完成…）传播给每个 handler， handler 对自己感兴趣的事件进行处理（重写了相应事件处理方法）
 *   4.eventLoop 可以理解为处理数据的工人
 *        eventLoop 可以管理多个 channel 的 io 操作，并且一旦 eventLoop 负责了某个 channel，就会将其与channel进行绑定，以后该 channel 中的 io 操作都由该 eventLoop 负责
 *        eventLoop 既可以执行 io 操作，也可以进行任务处理，每个 eventLoop 有自己的任务队列，队列里可以堆放多个 channel 的待处理任务，任务分为普通任务、定时任务
 *        eventLoop 按照 pipeline 顺序，依次按照 handler 的规划（代码）处理数据，可以为每个 handler 指定不同的 eventLoop
 */
@Slf4j(topic = "·")
public class HelloServer {

    public static void main(String[] args) {
        useSimpleChannelInboundHandlerAsHandler();
        //useChannelInboundHandlerAdapterAsHandler();
    }

    /**
     * 使用 SimpleChannelInboundHandler 作为入站处理器
     */
    private static void useSimpleChannelInboundHandlerAsHandler() {
        // 1.启动器，负责装配netty组件，启动服务器
        new ServerBootstrap()
            // 2.创建 NioEventLoopGroup，可以简单理解为 线程池 + Selector
            .group(new NioEventLoopGroup())
            // 3.选择服务器的 ServerSocketChannel 实现
            .channel(NioServerSocketChannel.class)
            // 4.child 负责处理读写，该方法决定了 child 执行哪些操作
            // ChannelInitializer 处理器，仅执行一次，它的作用是待客户端SocketChannel建立连接后，执行initChannel以便添加更多的处理器
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                    // 5.SocketChannel的处理器，使用StringDecoder解码，ByteBuf=>String
                    nioSocketChannel.pipeline().addLast(new StringDecoder());
                    // 6.SocketChannel的业务处理，使用上一个处理器的处理结果
                    nioSocketChannel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
                            log.info("msg: {}", msg);
                        }
                    });
                }
                // 7.ServerSocketChannel绑定8080端口
            }).bind(8080);
    }

    /**
     * 使用 ChannelInboundHandlerAdapter 作为入站处理器
     */
    private static void useChannelInboundHandlerAdapterAsHandler() {
        // 1.ServerBootstrap: 负责组装netty组件，启动netty
        new ServerBootstrap()
            // 2.EventLoop: WorkerEventLoop(selector,thread)，类似于accept方法的作用
            .group(new NioEventLoopGroup())
            // 3.选择netty使用的ServerSocket
            .channel(NioServerSocketChannel.class)
            // 4.boss负责连接，worker(child)负责处理读写，决定了worker(child)能执行什么操作
            .childHandler(
                // 5.代表和客户端进行数据读写的通道，负责添加别的handler，在initChannel()方法中进行添加
                new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 6.添加具体handler
                        ch.pipeline().addLast(new StringDecoder()); //将ByteBuf转换为字符串
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){ //自定义的handler
                            // 读事件
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

