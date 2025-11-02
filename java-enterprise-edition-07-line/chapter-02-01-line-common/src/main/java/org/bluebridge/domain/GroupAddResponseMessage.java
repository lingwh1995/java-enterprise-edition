package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lingwh
 * @desc 拉人进聊天组响应消息
 * @date 2025/11/2 11:15
 */
@Data
@AllArgsConstructor
public class GroupAddResponseMessage extends AbstractResponseMessage {

    public GroupAddResponseMessage(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    @Override
    public int getMessageType() {
        return MessageType.GROUP_ADD_RESPONSE_MESSAGE.getCode();
    }

}
