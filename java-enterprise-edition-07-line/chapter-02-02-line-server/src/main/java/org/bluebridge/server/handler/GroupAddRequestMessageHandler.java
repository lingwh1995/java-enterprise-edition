package org.bluebridge.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.domain.GroupAddRequestMessage;
import org.bluebridge.domain.GroupAddResponseMessage;
import org.bluebridge.server.session.GroupSession;
import org.bluebridge.server.session.Session;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lingwh
 * @desc 拉人进聊天组请求消息处理器
 * @date 2025/11/2 11:21
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class GroupAddRequestMessageHandler extends SimpleChannelInboundHandler<GroupAddRequestMessage> {

    @Resource
    private Session session;

    @Resource
    private GroupSession groupSession;

    @Override
    public void channelRead0(ChannelHandlerContext ctx, GroupAddRequestMessage groupAddRequestMessage) throws Exception {
        // 获取聊天组名称
        String groupName = groupAddRequestMessage.getGroupName();
        // 判断当前聊天组是否存在
        boolean isGroupNotExist = groupSession.isGroupNotExist(groupName);
        if(isGroupNotExist) {
            ctx.writeAndFlush(new GroupAddResponseMessage(false, "聊天组" + groupName + "不存在"));
            log.info("聊天组 {} 不存在", groupName);
            return;
        }
        // 获取加入聊天组的用户名
        String username = groupAddRequestMessage.getUsername();
        // 获取聊天组的所有者
        String owner = groupSession.getOwner(groupName);
        // 判断当前操作用户是否是聊天组所有者
        if(session.getUsername(ctx.channel()).equals(owner)) {
            // 将用户加入聊天组
            groupSession.joinMember(groupName, username);
            log.info("聊天组创建者 {} 将用户 {} 加入聊天组 {} ", owner, username, groupName);
            ctx.writeAndFlush(new GroupAddResponseMessage(true, username + "加入聊天组" + groupName + "成功"));
            // 通知聊天组中的所有用户，有新用户加入聊天组
            groupSession.getMembers(groupName).forEach(member -> {
                session.getChannel(member).writeAndFlush(new GroupAddResponseMessage(true, username + "加入聊天组" + groupName + "成功"));
            });
        }else {
            ctx.writeAndFlush(new GroupAddResponseMessage(false, "您不是聊天组创建者，无法执行添加用户到聊天组操作"));
            log.info("聊天组创建者 {} 将用户 {} 加入聊天组 {} 失败", owner, username, groupName);
        }
    }

}
