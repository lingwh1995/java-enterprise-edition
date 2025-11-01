package org.bluebridge.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.domain.GroupMembersRequestMessage;
import org.bluebridge.domain.GroupMembersResponseMessage;
import org.bluebridge.server.session.GroupSession;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author lingwh
 * @desc 查看群成员请求消息处理器
 * @date 2025/11/1 22:23
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class GroupMembersRequestMessageHandler extends SimpleChannelInboundHandler<GroupMembersRequestMessage> {

    @Resource
    private GroupSession groupSession;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMembersRequestMessage groupMembersRequestMessage) throws Exception {
        // 获取群名称
        String groupName = groupMembersRequestMessage.getGroupName();
        // 获取群组中的所有成员
        Set<String> members = groupSession.getMembers(groupName);
        // 构建查看群成员响应消息
        GroupMembersResponseMessage groupMembersResponseMessage = new GroupMembersResponseMessage(members);
        ctx.writeAndFlush(groupMembersResponseMessage);
    }

}
