package com.dragonsoft.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试CookieValue注解:
 *      1.用法类似于@RequestParam注解,可以获取所有的请求头信息
 *      2.也有required属性
 */
@Controller
public class CookieValueController {

    private static final String SUCCESS = "success";

    @RequestMapping(value="testCookieValue")
    public String testCookieValue(@CookieValue("JSESSIONID") String jsessionid){
        System.out.println("JSESSIONID:"+jsessionid);
        return SUCCESS;
    }
}
