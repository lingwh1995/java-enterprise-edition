package org.bluebridge.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.domain.GroupJoinRequestMessage;
import org.bluebridge.domain.GroupJoinResponseMessage;
import org.bluebridge.server.session.GroupSession;
import org.bluebridge.server.session.Session;
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
    private Session session;

    @Resource
    private GroupSession groupSession;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage groupJoinRequestMessage) throws Exception {
        // 获取聊天组名称
        String groupName = groupJoinRequestMessage.getGroupName();
        // 获取加入聊天组的用户名
        String username = groupJoinRequestMessage.getUsername();
        // 获取聊天组的所有者
        String owner = groupSession.getOwner(groupName);
        // 判断当前操作用户是否是聊天组所有者
        if(session.getUsername(ctx.channel()).equals(owner)) {
            // 将用户加入聊天组
            groupSession.joinMember(groupName, username);
            log.info("聊天组创建者 {} 将用户 {} 加入聊天组 {} ", owner, username, groupName);
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, username + "加入聊天组" + groupName + "成功"));
        }else {
            ctx.writeAndFlush(new GroupJoinResponseMessage(false, "您不是聊天组创建者，无法执行添加用户到聊天组操作"));
            log.info("聊天组创建者 {} 将用户 {} 加入聊天组 {} 失败", owner, username, groupName);
        }
    }

}
