package org.bluebridge.springmvctest.controller;

import org.bluebridge.springmvctest.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lingwh
 * @desc
 * @date   2019/7/22 14:26
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
