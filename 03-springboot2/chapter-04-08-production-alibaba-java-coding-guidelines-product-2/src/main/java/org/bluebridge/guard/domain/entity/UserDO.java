package org.bluebridge.guard.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bluebridge.common.domain.entity.BaseEntity;

/**
 * @author lingwh
 * @desc 用户实体
 * @date 2025/11/22 17:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDO extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     *  用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 创建人id
     */
    private Long createUser;

}
