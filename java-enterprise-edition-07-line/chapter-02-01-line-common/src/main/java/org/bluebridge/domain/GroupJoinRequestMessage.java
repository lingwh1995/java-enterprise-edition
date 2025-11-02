package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author lingwh
 * @desc 聊天组加入请求消息
 * @date 2025/11/2 11:12
 */
@Data
@ToString(callSuper = true)
@AllArgsConstructor
public class GroupJoinRequestMessage extends AbstractRequestMessage {

    private String groupName;
    private String username;

    @Override
    public int getMessageType() {
        return GROUP_CHAT_REQUEST_MESSAGE;
    }

}
