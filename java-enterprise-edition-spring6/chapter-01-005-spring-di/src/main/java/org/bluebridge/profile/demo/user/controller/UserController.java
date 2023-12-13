package org.bluebridge.profile.demo.user.controller;

import org.bluebridge.profile.demo.user.service.IUserService;

/**
 * 使用构造方式为属性注入引用类型的值
 */
public class UserController {

    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    public void deleteUserById(String id) {
        userService.deleteUserById(id);
    }
}
