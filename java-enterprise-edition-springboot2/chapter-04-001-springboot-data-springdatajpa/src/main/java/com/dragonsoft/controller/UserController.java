package com.dragonsoft.controller;

import com.dragonsoft.domain.User;
import com.dragonsoft.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ronin
 * @version V1.0
 * @since 2019/11/19 13:48
 */
@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @ResponseBody
    @RequestMapping("/user")
    public User findById(){
        return userService.findById("1");
    }
}
