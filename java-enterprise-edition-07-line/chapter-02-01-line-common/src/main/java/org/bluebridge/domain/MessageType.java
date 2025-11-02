package org.bluebridge.domain;

/**
 * @author lingwh
 * @desc 消息类型枚举类
 * @date 2025/11/2 12:56
 */
public enum MessageType {

    LOGIN_REQUEST_MESSAGE(0, LoginRequestMessage.class),
    LOGIN_RESPONSE_MESSAGE(1, LoginResponseMessage.class),
    CHAT_REQUEST_MESSAGE(2, ChatRequestMessage.class),
    CHAT_RESPONSE_MESSAGE(3, ChatResponseMessage.class),
    GROUP_CREATE_REQUEST_MESSAGE(4, GroupCreateRequestMessage.class),
    GROUP_CREATE_RESPONSE_MESSAGE(5, GroupCreateResponseMessage.class),
    GROUP_MEMBERS_REQUEST_MESSAGE(6, GroupMembersRequestMessage.class),
    GROUP_MEMBERS_RESPONSE_MESSAGE(7, GroupMembersResponseMessage.class),
    GROUP_ADD_REQUEST_MESSAGE(8, GroupAddRequestMessage.class),
    GROUP_ADD_RESPONSE_MESSAGE(9, GroupAddResponseMessage.class),
    GROUP_JOIN_REQUEST_MESSAGE(10, GroupJoinRequestMessage.class),
    GROUP_JOIN_RESPONSE_MESSAGE(11, GroupJoinResponseMessage.class);
//    GROUP_QUIT_REQUEST(10, GroupQuitRequestMessage.class),
//    GROUP_QUIT_RESPONSE(11, GroupQuitResponseMessage.class),
//    GROUP_CHAT_REQUEST(12, GroupChatRequestMessage.class),
//    GROUP_CHAT_RESPONSE(13, GroupChatResponseMessage.class),
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
