package org.bluebridge.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.domain.GroupQuitRequestMessage;
import org.bluebridge.domain.GroupQuitResponseMessage;
import org.bluebridge.server.session.GroupSession;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author lingwh
 * @desc 退出群组请求消息处理器
 * @date 2025/11/2 21:50
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class GroupQuitRequestMessageHandler extends SimpleChannelInboundHandler<GroupQuitRequestMessage> {

    @Resource
    private GroupSession groupSession;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupQuitRequestMessage groupQuitRequestMessage) throws Exception {
        // 获取聊天组名称
        String groupName = groupQuitRequestMessage.getGroupName();
        // 判断群组是否不存在
        boolean isGroupNotExist = groupSession.isGroupNotExist(groupName);
        if(isGroupNotExist) {
            log.info("群组 {} 不存在", groupName);
            ctx.writeAndFlush(new GroupQuitResponseMessage(false, "群组 " + groupName + " 不存在"));
            return;
        }
        // 获取用户名称
        String username = groupQuitRequestMessage.getUsername();
        // 推出群组
        groupSession.removeMember(groupName, username);
        // 通知其他群成员，某用户退出了群组
        Set<String> members = groupSession.getMembers(groupName);
        members.forEach(member -> {
            ctx.writeAndFlush(new GroupQuitResponseMessage(true, username + " 退出了群组 " + groupName));
        });
    }

}
