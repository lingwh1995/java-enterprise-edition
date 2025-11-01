package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

/**
 * @author lingwh
 * @desc 创建群聊请求消息
 * @date 2025/11/01 17:10
 */
@Data
@ToString(callSuper = true)
@AllArgsConstructor
public class GroupCreateRequestMessage extends AbstractRequestMessage {

    private String groupName;
    private Set<String> members;

    @Override
    public int getMessageType() {
        return GroupCreateRequestMessage;
    }

}
