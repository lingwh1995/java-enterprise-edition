package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author lingwh
 * @desc 创建群聊响应消息
 * @date 2025/11/01 17:14
 */
@Data
@ToString(callSuper = true)
public class GroupCreateResponseMessage extends AbstractResponseMessage {

    public GroupCreateResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return GroupCreateResponseMessage;
    }

}
