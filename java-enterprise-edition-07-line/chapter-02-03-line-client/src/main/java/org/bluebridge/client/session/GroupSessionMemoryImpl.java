package org.bluebridge.client.session;//package org.bluebridge.client.session;
//
//import io.netty.channel.Channel;
//import org.bluebridge.domain.Group;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.stream.Collectors;
//
///**
// * @author lingwh
// * @desc 聊天组会话管理实现类
// * @date 2025/10/25 12:09
// */
//@Service
//public class GroupSessionMemoryImpl implements GroupSession {
//
//    private final Map<String, Group> GROUP_MAP = new ConcurrentHashMap<>();
//
//    @Resource
//    private Session session;
//
//    @Override
//    public Group createGroup(String name, Set<String> members) {
//        Group group = new Group(name, members);
//        return GROUP_MAP.putIfAbsent(name, group);
//    }
//
//    @Override
//    public Group joinMember(String name, String member) {
//        return GROUP_MAP.computeIfPresent(name, (key, value) -> {
//            value.getMembers().add(member);
//            return value;
//        });
//    }
//
//    @Override
//    public Group removeMember(String name, String member) {
//        return GROUP_MAP.computeIfPresent(name, (key, value) -> {
//            value.getMembers().remove(member);
//            return value;
//        });
//    }
//
//    @Override
//    public Group removeGroup(String name) {
//        return GROUP_MAP.remove(name);
//    }
//
//    @Override
//    public Set<String> getMembers(String name) {
//        return GROUP_MAP.getOrDefault(name, Group.EMPTY_GROUP).getMembers();
//    }
//
//    @Override
//    public List<Channel> getMembersChannel(String name) {
//        return getMembers(name).stream()
//                .map(member -> session.getChannel(member))
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());
//    }
//
//}
