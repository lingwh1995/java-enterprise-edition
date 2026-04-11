package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author lingwh
 * @desc 查看群成员响应消息
 * @date 2025/11/1 22:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMembersResponseMessage extends AbstractResponseMessage {

    // 聊天室成员
    private Set<String> members;

    public GroupMembersResponseMessage(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    @Override
    public int getMessageType() {
        return MessageType.GROUP_MEMBERS_RESPONSE_MESSAGE.getCode();
    }

}
