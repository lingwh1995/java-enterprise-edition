package org.bulebridge.ioc.autowiredlocation.controller;

import org.bulebridge.ioc.autowiredlocation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author ronin
 */
@Controller
public class UserController {
    /**
     * 测试@Autowired写在字段上
     */
    @Autowired
    private UserService userService;

    public void say(){
        userService.say();
    }
}
