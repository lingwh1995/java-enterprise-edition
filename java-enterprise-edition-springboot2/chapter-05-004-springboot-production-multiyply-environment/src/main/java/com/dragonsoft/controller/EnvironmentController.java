package com.dragonsoft.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EnvironmentController {

    @Value("${spring.profiles.active}")
    private String environment;

    /**
     *  访问: http://localhost:8200/environment
     * @return
     */
    @ResponseBody
    @RequestMapping("/environment")
    public String getCurrentEnvironment() {
        return "当前环境:" + environment;
    }
}
