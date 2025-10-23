package org.bluebridge._19_protocol_design._04_custom_protocol_codec;

/**
 * @author lingwh
 * @desc 登录请求消息
 * @date 2025/10/15 17:30
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class LoginRequestMessage extends Message {

    private String username;
    private String password;
    private String nickname;

}

