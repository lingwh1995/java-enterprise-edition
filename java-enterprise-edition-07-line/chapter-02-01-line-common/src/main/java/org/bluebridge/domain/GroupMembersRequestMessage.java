package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author lingwh
 * @desc 查看群成员请求消息
 * @date 2025/11/1 22:09
 */
@Data
@ToString(callSuper = true)
@AllArgsConstructor
public class GroupMembersRequestMessage extends AbstractRequestMessage {

    private String groupName;

    @Override
    public int getMessageType() {
        return Message.GROUP_MEMBERS_REQUEST_MESSAGE;
    }

}
