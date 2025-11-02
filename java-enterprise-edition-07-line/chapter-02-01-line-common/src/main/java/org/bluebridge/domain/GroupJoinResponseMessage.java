package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author lingwh
 * @desc 聊天组加入响应消息
 * @date 2025/11/2 11:15
 */
@Data
@ToString(callSuper = true)
public class GroupJoinResponseMessage extends AbstractResponseMessage {

    public GroupJoinResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return GROUP_JOIN_RESPONSE_MESSAGE;
    }

}
