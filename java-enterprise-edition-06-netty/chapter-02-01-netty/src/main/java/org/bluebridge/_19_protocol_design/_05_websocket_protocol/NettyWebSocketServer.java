package org.bluebridge._19_protocol_design._05_websocket_protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author lingwh
 * @desc
 * @date 2025/10/16 14:07
 */
public class NettyWebSocketServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        // 创建主线程池
        EventLoopGroup boss = new NioEventLoopGroup();
        // 创建从线程池
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            //创建服务器类
            ServerBootstrap server = new ServerBootstrap();
            server.group(boss,worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline() ;
                        // websocket基于http协议，所需要的http编解码器
                        pipeline.addLast(new HttpServerCodec());
                        // 在http上有一些数据流产生，有大有小，我们对其进行处理，既然如此，我们需要使用netty对下数据流写提供支持，这个类叫ChunkedWriteHandler
                        pipeline.addLast(new ChunkedWriteHandler());
                        // 对httpMessage进行聚合处理，聚合成request或response
                        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
                        /**
                         * 本handler会帮你处理一些繁重复杂的事情
                         * 会帮你处理握手动作: handshaking (close、ping、pong) ping+pong = 心跳
                         * 对于websocket来讲，都是以frams进行传输的，不同的数据类型对应的fnams也不同
                         */
                        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

                        //自定义的handler
                        pipeline.addLast(new ChatHandler());
                    }
                });
            ChannelFuture future = server.bind(HOST, PORT).sync();
            future.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}
