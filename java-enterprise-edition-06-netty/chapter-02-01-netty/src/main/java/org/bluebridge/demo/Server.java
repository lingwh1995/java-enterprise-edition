package org.bluebridge.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author lingwh
 * @desc
 * @date 2025/9/30 17:25
 */
public class Server {

    public static void main( String[] args ) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childOption(ChannelOption.SO_REUSEADDR, true);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>(){
            @Override
            public void initChannel(SocketChannel ch)throws Exception{
                ch.pipeline().addLast(new EchoServerHandler());
            }
        });

        bootstrap.bind(8888).addListener((ChannelFutureListener) future -> {
            System.out.println("bind success in port: " + 8888);
        });
        System.out.println("server started!");
    }

}