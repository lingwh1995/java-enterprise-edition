package org.bluebridge.server.session;

import io.netty.channel.Channel;
import org.bluebridge.domain.Group;

import java.util.List;
import java.util.Set;

/**
 * @author lingwh
 * @desc 聊天组会话管理接口
 * @date 2025/10/25 11:54
 */
public interface GroupSession {

    /**
     * 创建聊天组
     * @param groupName 聊天组名称
     * @param members 聊天组成员
     * @param owner 聊天组创建者
     * @return 聊天组
     */
    Group createGroup(String groupName, Set<String> members, String owner);

    /**
     * 加入聊天组
     * @param groupName 聊天组名称
     * @param member 聊天组成员
     * @return 聊天组
     */
    Group joinMember(String groupName, String member);

    /**
     * 移除聊天组成员
     * @param groupName 聊天组名称
     * @param member 聊天组成员
     * @return 聊天组
     */
    Group removeMember(String groupName, String member);

    /**
     * 移除聊天组
     * @param groupName 聊天组名称
     * @return 聊天组
     */
    Group removeGroup(String groupName);

    /**
     * 获取聊天组成员
     * @param groupName 聊天组名称
     * @return 聊天组成员
     */
    Set<String> getMembers(String groupName);

    /**
     * 获取组成员的Channel
     * @param groupName 聊天组名称
     * @return 聊天组成员的Channel
     */
    List<Channel> getMembersChannel(String groupName);

    /**
     * 获取聊天组的所有者
     * @param groupName 聊天组名称
     * @return 聊天组的所有者
     */
    String getOwner(String groupName);

    /**
     * 根据聊天组名称获取聊天组
     * @param groupName 聊天组名称
     * @return 聊天组
     */
    Group getGroupByGroupName(String groupName);

    /**
     * 判断聊天组是否存在
     * @param groupName
     * @return
     */
    boolean isGroupNotExist(String groupName);

}
