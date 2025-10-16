package org.bluebridge._19_protocol_design._04_custom_protocol_codec;

/**
 * @author lingwh
 * @desc
 * @date 2025/10/15 17:30
 */
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class LoginRequestMessage extends Message {

    private String username;
    private String password;
    private String nickname;

    public LoginRequestMessage() {
    }

    public LoginRequestMessage(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public LoginRequestMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

}

