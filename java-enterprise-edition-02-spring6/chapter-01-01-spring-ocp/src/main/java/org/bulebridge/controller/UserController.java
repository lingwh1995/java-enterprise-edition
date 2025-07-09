package org.bulebridge.controller;

import org.bulebridge.domain.User;
import org.bulebridge.service.IUserService;
import org.bulebridge.service.UserServiceImpl;
/**
 * @author admin
 * @desc  用户控制器类，用于处理与用户相关的业务逻辑，包含根据用户ID查询用户信息的方法。
 * @date  2023/10/12 15:30
 */
public class UserController {
    private IUserService userService = new UserServiceImpl();

    public User findUserByUserId(String userId) {
        return userService.findUserByUserId(userId);
    }
}
