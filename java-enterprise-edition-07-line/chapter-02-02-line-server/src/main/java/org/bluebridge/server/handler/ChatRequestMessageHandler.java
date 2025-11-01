package org.bluebridge.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.domain.ChatRequestMessage;
import org.bluebridge.domain.ChatResponseMessage;
import org.bluebridge.server.session.Session;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {

    @Resource
    private Session session;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        // 获取消息接收方
        String to = msg.getTo();
        // 获取消息发送方
        String from = msg.getFrom();
        if(from.equals(to)) {
            ctx.writeAndFlush(new ChatResponseMessage(false, "不支持给自己发送消息"));
            return;
        }
        Channel channel = session.getChannel(to);
        if (channel != null) {
            // 在线
            channel.writeAndFlush(new ChatResponseMessage(msg.getFrom(), msg.getContent()));
            log.info("{} 给 {} 发送消息成功,消息内容 {}", from, to, msg.getContent());
        } else {
            // 不在线
            ctx.writeAndFlush(new ChatResponseMessage(false, "对方用户不存在或者不在线"));
        }
    }

}
