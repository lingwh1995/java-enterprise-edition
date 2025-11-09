package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lingwh
 * @desc 群聊退出响应消息
 * @date 2025/11/10 00:01
 */
@Data
@AllArgsConstructor
public class PingMessage extends AbstractRequestMessage {

    @Override
    public int getMessageType() {
        return MessageType.PING.getCode();
    }

}
