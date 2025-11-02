package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lingwh
 * @desc 群聊退出响应消息
 * @date 2025/11/2 21:43
 */
@Data
@AllArgsConstructor
public class GroupQuitResponseMessage extends AbstractResponseMessage {

    public GroupQuitResponseMessage(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    @Override
    public int getMessageType() {
        return MessageType.GROUP_QUIT_RESPONSE_MESSAGE.getCode();
    }

}
