package org.bluebridge._19_protocol_design._06_mysql_protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author lingwh
 * @desc MySQL代理服务器
 * @date 2025/10/27 17:25
 */
public class MySQLProxy {

    private final String targetHost;  // 目标MySQL服务器地址
    private final int targetPort;     // 目标MySQL服务器端口
    private final int proxyPort;      // 代理监听端口

    public MySQLProxy(String targetHost, int targetPort, int proxyPort) {
        this.targetHost = targetHost;
        this.targetPort = targetPort;
        this.proxyPort = proxyPort;
    }

    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 启动代理服务器（接收客户端连接）
            new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new ClientToProxyHandler(targetHost, targetPort));
                    }
                })
                .bind(proxyPort)
                .sync()
                .channel()
                .closeFuture()
                .sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 代理监听3307端口，转发到本地3306的MySQL
        new MySQLProxy("localhost", 3306, 3307).start();
    }

}

