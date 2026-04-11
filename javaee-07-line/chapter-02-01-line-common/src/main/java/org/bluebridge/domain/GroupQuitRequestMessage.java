package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lingwh
 * @desc 退出群聊请求消息
 * @date 2025/11/2 21:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupQuitRequestMessage extends AbstractRequestMessage {

    private String username;
    private String groupName;

    @Override
    public int getMessageType() {
        return MessageType.GROUP_QUIT_REQUEST_MESSAGE.getCode();
    }

}
