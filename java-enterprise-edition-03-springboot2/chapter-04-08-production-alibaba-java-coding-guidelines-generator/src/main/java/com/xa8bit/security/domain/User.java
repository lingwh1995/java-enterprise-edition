package com.xa8bit.security.domain;

import com.xa8bit.common.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author lingwh
 * @desc 用户实体
 * @date 2025/11/22 17:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    private Long id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String salt;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
