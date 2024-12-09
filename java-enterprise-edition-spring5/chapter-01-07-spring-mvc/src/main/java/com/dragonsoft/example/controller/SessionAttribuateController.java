package com.dragonsoft.example.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;


/**
 * SessionAttribuate注解:获取已经存在的session数据
 */
@Controller
public class SessionAttribuateController {

    private static final String SUCCESS = "success";

    @RequestMapping(value="testSessionAttribuate")
    public String testSessionAttribuate(@SessionAttribute("username") String username){
        System.out.println(username);
        return SUCCESS;
    }
}
