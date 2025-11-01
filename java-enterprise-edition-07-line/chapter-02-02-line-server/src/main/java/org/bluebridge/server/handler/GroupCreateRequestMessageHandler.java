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
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author lingwh
 * @desc 群聊创建请求消息处理器
 * @date 2025/11/1 17:19
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {

    @Resource
    private GroupSession groupSession;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage groupCreateRequestMessage) throws Exception {
        // 获取群聊名称
        String groupName = groupCreateRequestMessage.getGroupName();
        // 获取群成员
        Set<String> members = groupCreateRequestMessage.getMembers();
        // 创建群聊
        Group group = groupSession.createGroup(groupName, members);
        if(null == group) {
            log.info("群聊{}创建成功", groupName);
            // 给群聊创建者发送创建成功消息
            ctx.writeAndFlush(new GroupCreateResponseMessage(true, groupName + "创建成功"));
            // 给群成员发送创建成功消息
            List<Channel> membersChannel = groupSession.getMembersChannel(groupName);
            membersChannel.stream().forEach(memberChannel -> {
                memberChannel.writeAndFlush(new GroupCreateResponseMessage(true, "您已被拉入群聊 [" + groupName + "]"));
            });
        }else {
            log.info("群聊{}已经存在", groupName);
            ctx.writeAndFlush(new GroupCreateResponseMessage(false, "群组[" + groupName + "]已经存在"));
        }
    }

}
