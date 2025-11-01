//package org.bluebridge.client.session;
//
//import io.netty.channel.Channel;
//
///**
// * @author lingwh
// * @desc 会话管理接口
// * @date 2025/10/25 11:34
// */
//public interface Session {
//
//    /**
//     * 绑定会话
//     * @param channel 通道
//     * @param username 用户名
//     */
//    void bind(Channel channel, String username);
//
//    /**
//     * 解绑会话
//     * @param channel 通道
//     */
//    void unbind(Channel channel);
//
//    /**
//     * 获取会话属性
//     * @param channel 通道
//     * @param name 属性名
//     * @return 属性值
//     */
//    Object getAttribute(Channel channel, String name);
//
//    /**
//     * 设置会话属性
//     * @param channel 通道
//     * @param name 属性名
//     * @param value 属性值
//     */
//    void setAttribute(Channel channel, String name, Object value);
//
//    /**
//     * 获取会话通道
//     * @param username 用户名
//     * @return 通道
//     */
//    Channel getChannel(String username);
//
//}
