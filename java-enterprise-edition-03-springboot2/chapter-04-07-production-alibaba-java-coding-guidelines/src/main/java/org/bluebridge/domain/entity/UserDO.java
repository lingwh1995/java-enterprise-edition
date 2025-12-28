package org.bluebridge.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lingwh
 * @desc 用户实体
 * @date 2025/11/22 17:21
 */
@Data
public class UserDO {

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

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人id
     */
    private Long updateUser;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标记 0-未逻辑删除，1-已逻辑删除
     */
    private Integer isDeleted;

}
