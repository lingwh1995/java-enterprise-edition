package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lingwh
 * @desc 登录成功的响应消息
 * @date 2025/10/25 17:15
 */
@Data
@AllArgsConstructor
public class LoginResponseMessage extends AbstractResponseMessage {

    public LoginResponseMessage(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    @Override
    public int getMessageType() {
        return MessageType.LOGIN_RESPONSE_MESSAGE.getCode();
    }

    public boolean isSuccess() {
        return success;
    }
}