package org.bluebridge.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.domain.Group;
import org.bluebridge.domain.GroupAddResponseMessage;
import org.bluebridge.domain.GroupJoinRequestMessage;
import org.bluebridge.server.session.GroupSession;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lingwh
 * @desc 聊天组加入请求消息处理器
 * @date 2025/11/2 11:21
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class GroupJoinRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {

    @Resource
    private GroupSession groupSession;

    @Override
    public void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage groupJoinRequestMessage) throws Exception {
        // 获取聊天组名称
        String groupName = groupJoinRequestMessage.getGroupName();
        // 判断当前聊天组是否存在
        boolean isGroupNotExist = groupSession.isGroupNotExist(groupName);
        if(isGroupNotExist) {
            ctx.writeAndFlush(new GroupAddResponseMessage(false, "聊天组" + groupName + "不存在"));
            log.info("聊天组 {} 不存在", groupName);
            return;
        }
        // 获取加入聊天组的用户名
        String username = groupJoinRequestMessage.getUsername();
        // 获取聊天组
        Group group = groupSession.getGroupByGroupName(groupName);
        // 判断聊天组是否存在
        if(group != null) {
            // 将用户加入聊天组
            groupSession.joinMember(groupName, username);
            log.info("用户 {} 加入聊天组 {} 成功", username, groupName);
            ctx.writeAndFlush(new GroupAddResponseMessage(true, username + "加入聊天组" + groupName + "成功"));
        }else {
            ctx.writeAndFlush(new GroupAddResponseMessage(false, "聊天组" + groupName + "不存在"));
            log.info("聊天组 {} 不存在", groupName);
        }
    }

}
