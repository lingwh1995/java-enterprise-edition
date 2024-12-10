package org.bluebridge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 访问下面链接测试功能
 *      http://localhost:8080
 */
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    //访问    http://localhost:8080/      查看效果


    @RequestMapping("/")
    public String index() {
        logger.info("跳转到首页...");
        return "index";
    }
}
