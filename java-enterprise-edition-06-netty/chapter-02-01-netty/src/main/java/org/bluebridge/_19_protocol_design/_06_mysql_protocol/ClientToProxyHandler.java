package org.bluebridge._19_protocol_design._06_mysql_protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lingwh
 * @desc 客户端到代理的处理器：负责连接MySQL服务器并转发数据
 * @date 2025/10/27 17:25
 */
@Slf4j
public class ClientToProxyHandler extends ChannelInboundHandlerAdapter {

    private final String targetHost;
    private final int targetPort;
    // 代理到MySQL服务器的通道
    private Channel serverChannel;

    public ClientToProxyHandler(String targetHost, int targetPort) {
        this.targetHost = targetHost;
        this.targetPort = targetPort;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 客户端连接后，代理主动连接MySQL服务器
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(ctx.channel().eventLoop())
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    // 添加服务器到代理的处理器（转发服务器响应给客户端）
                    ch.pipeline().addLast(new ServerToProxyHandler(ctx.channel()));
                }
            });

        // 连接MySQL服务器
        bootstrap.connect(targetHost, targetPort).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                serverChannel = future.channel();
            } else {
                // 连接失败则关闭客户端连接
                ctx.close();
            }
        });
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 客户端数据转发到MySQL服务器
        if (serverChannel != null && serverChannel.isActive()) {
            ByteBuf buf = (ByteBuf) msg;
            // 这里可以添加自定义逻辑（如解析MySQL协议、日志记录等）
            log.info("客户端 -> 服务器: {} bytes", buf.readableBytes());
            serverChannel.writeAndFlush(msg);
        } else {
            ((ByteBuf) msg).release();  // 释放未转发的缓冲区
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (serverChannel != null) {
            // 客户端断开，关闭服务器连接
            serverChannel.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
