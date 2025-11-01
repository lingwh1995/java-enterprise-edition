package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author lingwh
 * @desc 登录请求消息
 * @date 2025/10/25 17:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class LoginRequestMessage extends AbstractRequestMessage {

    private String username;
    private String password;

    @Override
    public int getMessageType() {
        return Message.LOGIN_REQUEST_MESSAGE;
    }

}
