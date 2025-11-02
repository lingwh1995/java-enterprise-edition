package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author lingwh
 * @desc 创建聊天组请求消息
 * @date 2025/11/01 17:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupCreateRequestMessage extends AbstractRequestMessage {

    private String groupName;
    private Set<String> members;

    @Override
    public int getMessageType() {
        return MessageType.GROUP_CREATE_REQUEST_MESSAGE.getCode();
    }

}
