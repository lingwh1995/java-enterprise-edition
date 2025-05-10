package org.bluebridge.springmvctest.controller;

import org.bluebridge.springmvctest.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author ronin
 * @version V1.0
 * @desc    SpringMVC单元测试
 * @since 2019/7/22 14:26
 */
@Controller
public class SpringMVCJUnit  extends BaseTest{
    @Autowired
    private IUserService userService;

//    public String eat(){
//        userService.eat();
//        return "";
//    }
    @Test
    public void fun(){
        userService.eat();
    }

}
