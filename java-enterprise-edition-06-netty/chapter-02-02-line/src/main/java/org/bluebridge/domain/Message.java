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

    public static final int LoginRequestMessage = 0;
    public static final int LoginResponseMessage = 1;
    public static final int ChatRequestMessage = 2;
    public static final int ChatResponseMessage = 3;
    public static final int GroupCreateRequestMessage = 4;
    public static final int GroupCreateResponseMessage = 5;
    public static final int GroupJoinRequestMessage = 6;
    public static final int GroupJoinResponseMessage = 7;
    public static final int GroupQuitRequestMessage = 8;
    public static final int GroupQuitResponseMessage = 9;
    public static final int GroupChatRequestMessage = 10;
    public static final int GroupChatResponseMessage = 11;
    public static final int GroupMembersRequestMessage = 12;
    public static final int GroupMembersResponseMessage = 13;
    public static final int PingMessage = 14;
    public static final int PongMessage = 15;
    /**
     * 请求类型 byte 值
     */
    public static final int RPC_MESSAGE_TYPE_REQUEST = 101;
    /**
     * 响应类型 byte 值
     */
    public static final int  RPC_MESSAGE_TYPE_RESPONSE = 102;

    private static final Map<Integer, Class<? extends Message>> MESSAGE_CLASSES = new HashMap<>();

    static {
        MESSAGE_CLASSES.put(LoginRequestMessage, LoginRequestMessage.class);
        MESSAGE_CLASSES.put(LoginResponseMessage, LoginResponseMessage.class);
//        MESSAGE_CLASSES.put(ChatRequestMessage, ChatRequestMessage.class);
//        MESSAGE_CLASSES.put(ChatResponseMessage, ChatResponseMessage.class);
//        MESSAGE_CLASSES.put(GroupCreateRequestMessage, GroupCreateRequestMessage.class);
//        MESSAGE_CLASSES.put(GroupCreateResponseMessage, GroupCreateResponseMessage.class);
//        MESSAGE_CLASSES.put(GroupJoinRequestMessage, GroupJoinRequestMessage.class);
//        MESSAGE_CLASSES.put(GroupJoinResponseMessage, GroupJoinResponseMessage.class);
//        MESSAGE_CLASSES.put(GroupQuitRequestMessage, GroupQuitRequestMessage.class);
//        MESSAGE_CLASSES.put(GroupQuitResponseMessage, GroupQuitResponseMessage.class);
//        MESSAGE_CLASSES.put(GroupChatRequestMessage, GroupChatRequestMessage.class);
//        MESSAGE_CLASSES.put(GroupChatResponseMessage, GroupChatResponseMessage.class);
//        MESSAGE_CLASSES.put(GroupMembersRequestMessage, GroupMembersRequestMessage.class);
//        MESSAGE_CLASSES.put(GroupMembersResponseMessage, GroupMembersResponseMessage.class);
    }

}
