package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lingwh
 * @desc 群聊响应消息
 * @date 2025/11/2 20:17
 */
@Data
@AllArgsConstructor
public class GroupChatResponseMessage extends AbstractResponseMessage {

    private String from;
    private String content;

    public GroupChatResponseMessage(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    public GroupChatResponseMessage(boolean success, String from, String content) {
        this.success = success;
        this.from = from;
        this.content = content;
    }

    @Override
    public int getMessageType() {
        return MessageType.GROUP_CHAT_RESPONSE_MESSAGE.getCode();
    }

}
