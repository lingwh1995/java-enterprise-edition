package org.bluebridge.annotation.demo.user.controller;

import org.bluebridge.annotation.demo.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 使用构造方式为属性注入引用类型的值
 */
@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    public void deleteUserById(String id) {
        userService.deleteUserById(id);
    }
}
