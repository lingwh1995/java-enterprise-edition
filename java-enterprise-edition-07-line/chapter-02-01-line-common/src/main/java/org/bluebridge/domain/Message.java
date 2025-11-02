package org.bluebridge.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lingwh
 * @desc 消息抽象类
 * @date 2025/10/25 12:41
 */
@Data
public abstract class Message implements Serializable {

    /**
     * 根据消息类型字节，获得对应的消息 class
     * @param messageType 消息类型字节
     * @return 消息 class
     */
    public static Class<? extends Message> getMessageClass(int messageType) {
        return MESSAGE_CLASSES.get(messageType);
    }

    private int sequenceId;

    private int messageType;

    public abstract int getMessageType();

    public Message() {
        this.messageType = getMessageType();
    }

    // 登录请求消息
    public static final int LOGIN_REQUEST_MESSAGE = 0;
    // 登录响应消息
    public static final int LOGIN_RESPONSE_MESSAGE = 1;
    // 单聊请求消息
    public static final int CHAT_REQUEST_MESSAGE = 2;
    // 单聊响应消息
    public static final int CHAT_RESPONSE_MESSAGE = 3;
    // 聊天组创建请求消息
    public static final int GROUP_CREATE_REQUEST_MESSAGE = 4;
    // 聊天组创建响应消息
    public static final int GROUP_CREATE_RESPONSE_MESSAGE = 5;
    // 群成员查看请求消息
    public static final int GROUP_MEMBERS_REQUEST_MESSAGE = 6;
    // 群成员查看响应消息
    public static final int GROUP_MEMBERS_RESPONSE_MESSAGE = 7;
    // 群加入请求消息
    public static final int GROUP_JOIN_REQUEST_MESSAGE = 8;
    // 群加入响应消息
    public static final int GROUP_JOIN_RESPONSE_MESSAGE = 9;
    // 群退出请求消息
    public static final int GROUP_QUIT_REQUEST_MESSAGE = 10;
    // 群退出响应消息
    public static final int GROUP_QUIT_RESPONSE_MESSAGE = 11;
    // 群聊请求消息
    public static final int GROUP_CHAT_REQUEST_MESSAGE = 12;
    // 群聊响应消息
    public static final int GROUP_CHAT_RESPONSE_MESSAGE = 13;
    // 心跳请求消息
    public static final int PING_MESSAGE = 14;
    // 心跳响应消息
    public static final int PONG_MESSAGE = 15;

    private static final Map<Integer, Class<? extends Message>> MESSAGE_CLASSES = new HashMap<>();

    static {
        MESSAGE_CLASSES.put(LOGIN_REQUEST_MESSAGE, LoginRequestMessage.class);
        MESSAGE_CLASSES.put(LOGIN_RESPONSE_MESSAGE, LoginResponseMessage.class);
        MESSAGE_CLASSES.put(CHAT_REQUEST_MESSAGE, ChatRequestMessage.class);
        MESSAGE_CLASSES.put(CHAT_RESPONSE_MESSAGE, ChatResponseMessage.class);
        MESSAGE_CLASSES.put(GROUP_CREATE_REQUEST_MESSAGE, GroupCreateRequestMessage.class);
        MESSAGE_CLASSES.put(GROUP_CREATE_RESPONSE_MESSAGE, GroupCreateResponseMessage.class);
        MESSAGE_CLASSES.put(GROUP_MEMBERS_REQUEST_MESSAGE, GroupMembersRequestMessage.class);
        MESSAGE_CLASSES.put(GROUP_MEMBERS_RESPONSE_MESSAGE, GroupMembersResponseMessage.class);
        MESSAGE_CLASSES.put(GROUP_JOIN_REQUEST_MESSAGE, GroupJoinRequestMessage.class);
        MESSAGE_CLASSES.put(GROUP_JOIN_RESPONSE_MESSAGE, GroupJoinResponseMessage.class);
//        MESSAGE_CLASSES.put(GroupQuitRequestMessage, GroupQuitRequestMessage.class);
//        MESSAGE_CLASSES.put(GroupQuitResponseMessage, GroupQuitResponseMessage.class);
//        MESSAGE_CLASSES.put(GroupChatRequestMessage, GroupChatRequestMessage.class);
//        MESSAGE_CLASSES.put(GroupChatResponseMessage, GroupChatResponseMessage.class);
    }

}
