package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lingwh
 * @desc 拉人进聊天组请求消息
 * @date 2025/11/2 11:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupAddRequestMessage extends AbstractRequestMessage {

    private String groupName;
    private String username;

    @Override
    public int getMessageType() {
        return MessageType.GROUP_ADD_REQUEST_MESSAGE.getCode();
    }

}
