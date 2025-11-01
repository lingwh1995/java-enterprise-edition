package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

/**
 * @author lingwh
 * @desc 查看群成员响应消息
 * @date 2025/11/1 22:16
 */
@Data
@ToString(callSuper = true)
@AllArgsConstructor
public class GroupMembersResponseMessage extends AbstractRequestMessage {

    // 聊天室成员
    private Set<String> members;

    @Override
    public int getMessageType() {
        return Message.GROUP_CREATE_RESPONSE_MESSAGE;
    }

}
