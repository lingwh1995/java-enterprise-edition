package org.bluebridge.client.session;//package org.bluebridge.client.session;
//
//import io.netty.channel.Channel;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author lingwh
// * @desc 会话管理内存实现类
// * @date 2025/10/25 11:44
// */
//@Service
//public class SessionMemoryImpl implements  Session{
//
//    // 用户名-通道映射表
//    private static final Map<String, Channel> USERNAME_CHANNEL_MAP = new ConcurrentHashMap<>();
//    // 通道-用户名映射表
//    private static final Map<Channel, String> CHANNEL_USERNAME_MAP = new ConcurrentHashMap<>();
//    // 通道-属性映射表
//    private static final Map<Channel, Map<String, Object>> CHANNEL_ATTRIBUTE_MAP = new ConcurrentHashMap<>();
//
//    @Override
//    public void bind(Channel channel, String username) {
//        USERNAME_CHANNEL_MAP.put(username, channel);
//        CHANNEL_USERNAME_MAP.put(channel, username);
//        CHANNEL_ATTRIBUTE_MAP.put(channel, new ConcurrentHashMap<>());
//    }
//
//    @Override
//    public void unbind(Channel channel) {
//        String username = CHANNEL_USERNAME_MAP.remove(channel);
//        USERNAME_CHANNEL_MAP.remove(username);
//        CHANNEL_ATTRIBUTE_MAP.remove(channel);
//    }
//
//    @Override
//    public Object getAttribute(Channel channel, String name) {
//        return CHANNEL_ATTRIBUTE_MAP.get(channel).get(name);
//    }
//
//    @Override
//    public void setAttribute(Channel channel, String name, Object value) {
//        CHANNEL_ATTRIBUTE_MAP.get(channel).put(name, value);
//    }
//
//    @Override
//    public Channel getChannel(String username) {
//        return null;
//    }
//
//}
