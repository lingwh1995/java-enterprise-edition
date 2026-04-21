package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lingwh
 * @desc 查看群成员请求消息
 * @date 2025/11/1 22:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMembersRequestMessage extends AbstractRequestMessage {

    private String groupName;

    @Override
    public int getMessageType() {
        return MessageType.GROUP_MEMBERS_REQUEST_MESSAGE.getCode();
    }

}
