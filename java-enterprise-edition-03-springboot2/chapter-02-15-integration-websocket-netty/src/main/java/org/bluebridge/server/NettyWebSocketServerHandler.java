package org.bluebridge.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lingwh
 * @desc 服务端处理器
 * @date 2025/10/21 16:29
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 使用Map存储Channel与用户的关系
    private static final Map<Channel, String> CHANNEL_USER_MAP = new ConcurrentHashMap<>();

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端连接: {}", ctx.channel().id().asLongText());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 新连接建立时记录
        Channel channel = ctx.channel();
        // 可以根据业务逻辑关联用户标识
        CHANNEL_USER_MAP.put(channel, "user-" + channel.id().asShortText());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String request = msg.text();
        log.info("收到消息: {}", request);

        // 回复客户端
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器回复: " + request));

        Channel channel = ctx.channel();
        String userId = CHANNEL_USER_MAP.get(channel);
        // 处理特定用户的消息
        //
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端断开连接: {}", ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}