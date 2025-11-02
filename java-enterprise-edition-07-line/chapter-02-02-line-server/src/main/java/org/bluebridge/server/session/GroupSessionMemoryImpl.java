package org.bluebridge.server.session;

import io.netty.channel.Channel;
import org.bluebridge.domain.Group;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author lingwh
 * @desc 聊天组会话管理实现类
 * @date 2025/10/25 12:09
 */
@Service
public class GroupSessionMemoryImpl implements GroupSession {

    private final Map<String, Group> GROUP_MAP = new ConcurrentHashMap<>();

    @Resource
    private Session session;

    @Override
    public Group createGroup(String groupName, Set<String> members, String owner) {
        Group group = new Group(groupName, members, owner);
        return GROUP_MAP.putIfAbsent(groupName, group);
    }

    @Override
    public Group joinMember(String groupName, String member) {
        return GROUP_MAP.computeIfPresent(groupName, (key, value) -> {
            value.getMembers().add(member);
            return value;
        });
    }

    @Override
    public Group removeMember(String groupName, String member) {
        return GROUP_MAP.computeIfPresent(groupName, (key, value) -> {
            value.getMembers().remove(member);
            return value;
        });
    }

    @Override
    public Group removeGroup(String groupName) {
        return GROUP_MAP.remove(groupName);
    }

    @Override
    public Set<String> getMembers(String groupName) {
        return GROUP_MAP.getOrDefault(groupName, Group.EMPTY_GROUP).getMembers();
    }

    @Override
    public List<Channel> getMembersChannel(String groupName) {
        return getMembers(groupName).stream()
                .map(member -> session.getChannel(member))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public String getOwner(String groupName) {
        return GROUP_MAP.getOrDefault(groupName, Group.EMPTY_GROUP).getOwner();
    }

}
