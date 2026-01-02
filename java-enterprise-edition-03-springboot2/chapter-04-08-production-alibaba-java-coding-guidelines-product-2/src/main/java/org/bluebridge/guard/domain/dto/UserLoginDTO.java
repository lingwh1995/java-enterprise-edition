package org.bluebridge.guard.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/28 12:23
 */
@Data
public class UserLoginDTO {

    /**
     *  用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

}
