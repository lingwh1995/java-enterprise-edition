package org.bluebridge._19_group_chat.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge._19_group_chat.message.PingMessage;

/**
 * @author lingwh
 * @desc 心跳包处理器
 * @date 2025/10/16 10:53
 */
@Slf4j
@ChannelHandler.Sharable
public class PingMessageHandler extends SimpleChannelInboundHandler<PingMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PingMessage msg) throws Exception {
        // 接收到客户端的心跳包，可以记录日志
        log.info("收到客户端心跳包: {}", msg);
        // 服务端不需要回复，只需要处理即可重置读空闲计时器
    }

}

