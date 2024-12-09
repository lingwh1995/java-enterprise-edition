package com.dragonsoft.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * SpringMVC的RequestParam注解:
 *      value:参数值
 *      required:是否一定要有这个参数，默认为true,前台不传递会报错
 *      defaultValue:设置默认值(如果required=true,前台又没有传递该该参数，
 *                              此时defaultValue设置的值生效,不设置defaultValue时此种情况会报错)
 */
@Controller
public class RequestParamController {

    private static final String SUCCESS = "success";

    /**
     * RequestParam注解
     * @return
     */
    @RequestMapping(value="testRequestParam")
    public String testRequestParam(@RequestParam(value="username") String username,
                                   @RequestParam(value="age") Integer age,
                                   @RequestParam(value="school",required=false,defaultValue = "xaufe") String school){
        System.out.println("username:"+username+"----age:"+age);
        System.out.println("school:"+school);
        return SUCCESS;
    }
}
