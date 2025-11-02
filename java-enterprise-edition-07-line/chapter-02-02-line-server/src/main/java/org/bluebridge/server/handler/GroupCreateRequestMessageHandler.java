package org.bluebridge.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.domain.Group;
import org.bluebridge.domain.GroupCreateRequestMessage;
import org.bluebridge.domain.GroupCreateResponseMessage;
import org.bluebridge.server.session.GroupSession;
import org.bluebridge.server.session.Session;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author lingwh
 * @desc 聊天组创建请求消息处理器
 * @date 2025/11/1 17:19
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {

    @Resource
    private Session session;

    @Resource
    private GroupSession groupSession;

    @Override
    public void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage groupCreateRequestMessage) throws Exception {
        // 获取聊天组名称
        String groupName = groupCreateRequestMessage.getGroupName();
        // 获取群成员
        Set<String> members = groupCreateRequestMessage.getMembers();
        // 根据Channel获取Channel对应的用户名
        String owner = session.getUsername(ctx.channel());
        // 创建聊天组
        Group group = groupSession.createGroup(groupName, members, owner);
        if(null == group) {
            log.info("聊天组 {} 创建成功", groupName);
            // 给聊天组创建者发送创建成功消息
            ctx.writeAndFlush(new GroupCreateResponseMessage(true, groupName + "创建成功"));
            // 给群成员发送创建成功消息
            List<Channel> membersChannel = groupSession.getMembersChannel(groupName);
            membersChannel.stream().forEach(memberChannel -> {
                memberChannel.writeAndFlush(new GroupCreateResponseMessage(true, "您已被拉入聊天组 [" + groupName + "]"));
            });
        }else {
            log.info("聊天组 {} 已经存在", groupName);
            ctx.writeAndFlush(new GroupCreateResponseMessage(false, "聊天组[" + groupName + "]已经存在"));
        }
    }

}
