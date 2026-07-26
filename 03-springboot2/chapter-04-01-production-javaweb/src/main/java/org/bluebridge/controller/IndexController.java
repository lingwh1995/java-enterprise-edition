package org.bluebridge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页控制器
 * 注意：启动时要使用spring-boot-maven-plugin的springboot:run 启动，否则会报 404
 *
 * @author lingwh
 * @date 2024/11/15 11:42
 */
@Controller
public class IndexController {

    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("msg","I am msg from IndexController!");
        return "hello";
    }
}
