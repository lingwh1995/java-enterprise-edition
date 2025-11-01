package org.bluebridge.domain;

import lombok.Data;
import lombok.ToString;

/**
 * @author lingwh
 * @desc 登录成功的响应消息
 * @date 2025/10/25 17:15
 */
@Data
@ToString(callSuper = true)
public class LoginResponseMessage extends AbstractResponseMessage {

    public LoginResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return Message.LOGIN_RESPONSE_MESSAGE;
    }

}