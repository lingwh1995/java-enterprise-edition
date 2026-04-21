package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lingwh
 * @desc 群聊退出响应消息
 * @date 2025/11/10 00:04
 */
@Data
@AllArgsConstructor
public class PongMessage extends AbstractResponseMessage {

    @Override
    public int getMessageType() {
        return MessageType.PONG.getCode();
    }

}
