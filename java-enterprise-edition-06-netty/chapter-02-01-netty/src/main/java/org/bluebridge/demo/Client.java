package org.bluebridge.demo;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author lingwh
 * @desc
 * @date 2025/9/30 17:27
 */
public class Client {

    private static final String SERVER_HOST = "localhost"; // 请注意用实际地址替换a.b.c.d

    public static void main(String[] args) {

        System.out.println("client starting....");
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(4);
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_REUSEADDR, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new IdleStateHandler(0, 0, 120));
                pipeline.addLast(new EchoClientHandler());
            }
        });

        // 创建30000个客户端连接
        for (int i = 0;  i < 30000; i++) {
            try {
                ChannelFuture channelFuture = bootstrap.connect(SERVER_HOST, 8888);
                channelFuture.addListener((ChannelFutureListener) future -> {
                    if (!future.isSuccess()) {
                        System.out.println("connect failed, exit!");
                    }
                });
                channelFuture.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try{
            System.in.read();
        }catch (Exception e){

        }
    }
}
