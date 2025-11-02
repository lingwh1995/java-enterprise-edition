package org.bluebridge.domain;

/**
 * @author lingwh
 * @desc 消息类型枚举类
 * @date 2025/11/2 12:56
 */
public enum MessageType {

    // 登录请求消息
    LOGIN_REQUEST_MESSAGE(0, LoginRequestMessage.class),
    // 登录响应消息
    LOGIN_RESPONSE_MESSAGE(1, LoginResponseMessage.class),
    // 聊天请求消息
    CHAT_REQUEST_MESSAGE(2, ChatRequestMessage.class),
    // 聊天响应消息
    CHAT_RESPONSE_MESSAGE(3, ChatResponseMessage.class),
    // 创建聊天组请求消息
    GROUP_CREATE_REQUEST_MESSAGE(4, GroupCreateRequestMessage.class),
    // 创建聊天组响应消息
    GROUP_CREATE_RESPONSE_MESSAGE(5, GroupCreateResponseMessage.class),
    // 获取聊天组成员请求消息
    GROUP_MEMBERS_REQUEST_MESSAGE(6, GroupMembersRequestMessage.class),
    // 获取聊天组成员响应消息
    GROUP_MEMBERS_RESPONSE_MESSAGE(7, GroupMembersResponseMessage.class),
    // 加入聊天组请求消息
    GROUP_ADD_REQUEST_MESSAGE(8, GroupAddRequestMessage.class),
    // 加入聊天组响应消息
    GROUP_ADD_RESPONSE_MESSAGE(9, GroupAddResponseMessage.class),
    // 加入聊天组请求
    GROUP_JOIN_REQUEST_MESSAGE(10, GroupJoinRequestMessage.class),
    // 加入聊天组响应消息
    GROUP_JOIN_RESPONSE_MESSAGE(11, GroupJoinResponseMessage.class),
    // 聊天组聊天请求消息
    GROUP_CHAT_REQUEST_MESSAGE(12, GroupChatRequestMessage.class),
    // 聊天组聊天响应消息
    GROUP_CHAT_RESPONSE_MESSAGE(13, GroupChatResponseMessage.class),
    // 退出聊天组请求消息
    GROUP_QUIT_REQUEST_MESSAGE(14, GroupQuitRequestMessage.class),
    // 退出聊天组响应消息
    GROUP_QUIT_RESPONSE_MESSAGE(15, GroupQuitResponseMessage.class);
//    PING(14, PingMessage.class),
//    PONG(15, PongMessage.class);

    private final int code;
    private final Class<? extends Message> messageClass;

    MessageType(int code, Class<? extends Message> messageClass) {
        this.code = code;
        this.messageClass = messageClass;
    }

    public int getCode() {
        return code;
    }

    public Class<? extends Message> getMessageClass() {
        return messageClass;
    }

    public static Class<? extends Message> getMessageClassByCode(int code) {
        for (MessageType type : values()) {
            if (type.code == code) {
                return type.messageClass;
            }
        }
        return null;
    }

}
