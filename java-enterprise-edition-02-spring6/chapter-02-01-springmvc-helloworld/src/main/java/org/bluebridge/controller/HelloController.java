package org.bluebridge.controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping("/")
    public String index() {
        logger.info("跳转到首页...");
        return "index";
    }

    @RequestMapping("/hello")
    public Map<String,String> toTarget() {
        logger.info("跳转到hello页面...");
        Map<String, String> map = new HashMap<>();
        map.put("message","hello world");
        return map;
    }
}
