package org.bluebridge.controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/viewResolver")
@Controller
public class ViewController {

    private static final Logger logger = LoggerFactory.getLogger(ViewController.class);

    /**
     * 测试ThymeleafViewResolver
     * @return
     */
    @RequestMapping("/testThymeleafViewResolver")
    public String testThymeleafViewResolver() {
        logger.info("使用ThymeleafViewResolver解析:跳转到success页面...");
        return "success";
    }

    /**
     * 测试请求转发
     * @return
     */
    @RequestMapping("/testRequestForward")
    public String testRequestForward() {
        logger.info("使用请求转发:跳转到路径为 /viewResolver/testThymeleafViewResolver 请求...");
        return "forward:/viewResolver/testThymeleafViewResolver";
    }

    /**
     * 测试请求重定向  重定向到百度
     * @return
     */
    @RequestMapping("/testRequestRedirectToBaidu")
    public String testRequestRedirectToBaidu() {
        logger.info("使用请求转发:跳转到百度...");
        return "redirect:https://www.baidu.com/";
    }

    /**
     * 测试请求重定向  重定向到首页
     * @return
     */
    @RequestMapping("/testRequestRedirectToIndex")
    public String    testRequestRedirectToIndex() {
        logger.info("使用请求转发:跳转到首页...");
        return "redirect:/";
    }
}
