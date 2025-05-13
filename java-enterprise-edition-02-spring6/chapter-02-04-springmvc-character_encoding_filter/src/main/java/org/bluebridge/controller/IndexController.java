package org.bluebridge.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/")
    public String index(HttpServletResponse response) {
        logger.info("跳转到首页...");
        return "index";
    }
}
