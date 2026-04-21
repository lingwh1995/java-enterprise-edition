package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lingwh
 * @desc 聊天组加入请求消息
 * @date 2025/11/2 11:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupJoinRequestMessage extends AbstractRequestMessage {

    private String groupName;
    private String username;

    @Override
    public int getMessageType() {
        return MessageType.GROUP_JOIN_REQUEST_MESSAGE.getCode();
    }

}
