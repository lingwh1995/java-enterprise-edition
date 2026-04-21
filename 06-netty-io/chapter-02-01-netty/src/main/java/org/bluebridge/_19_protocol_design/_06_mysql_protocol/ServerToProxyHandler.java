package org.bluebridge._19_protocol_design._06_mysql_protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lingwh
 * @desc 服务器到代理的处理器：负责将MySQL响应转发给客户端
 * @date 2025/10/27 17:26
 */
@Slf4j
public class ServerToProxyHandler extends ChannelInboundHandlerAdapter {
    // 客户端通道
    private final Channel clientChannel;

    public ServerToProxyHandler(Channel clientChannel) {
        this.clientChannel = clientChannel;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 服务器响应转发到客户端
        if (clientChannel.isActive()) {
            ByteBuf buf = (ByteBuf) msg;
            // 这里可以添加自定义逻辑（如解析响应、修改数据等）
            log.info("服务器 -> 客户端: {} bytes", buf.readableBytes());
            clientChannel.writeAndFlush(msg);
        } else {
            // 释放未转发的缓冲区
            ((ByteBuf) msg).release();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        // 服务器断开，关闭客户端连接
        clientChannel.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
