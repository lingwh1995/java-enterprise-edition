package org.bluebridge.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.domain.GroupChatRequestMessage;
import org.bluebridge.domain.GroupChatResponseMessage;
import org.bluebridge.server.session.GroupSession;
import org.bluebridge.server.session.Session;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author lingwh
 * @desc
 * @date 2025/11/2 20:22
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class GroupChatRequestHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {

    @Resource
    private Session session;

    @Resource
    private GroupSession groupSession;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage groupChatRequestMessage) throws Exception {
        // 获取聊天组名称
        String groupName = groupChatRequestMessage.getGroupName();
        // 判断当前聊天组是否不存在
        boolean isGroupNotExist = groupSession.isGroupNotExist(groupName);
        if(isGroupNotExist) {
            ctx.writeAndFlush(new GroupChatResponseMessage(false, "聊天组" + groupName + "不存在"));
            return;
        }
        // 获取发送者用户名
        String sendUsername = session.getUsername(ctx.channel());
        // 遍历群组成员，将消息发送给每个群组成员
        groupSession.getMembers(groupName).stream().forEach(member -> {
            GroupChatResponseMessage groupChatResponseMessage = new GroupChatResponseMessage(true, sendUsername, groupChatRequestMessage.getContent());
            session.getChannel(member).writeAndFlush(groupChatResponseMessage);
        });
    }

}
