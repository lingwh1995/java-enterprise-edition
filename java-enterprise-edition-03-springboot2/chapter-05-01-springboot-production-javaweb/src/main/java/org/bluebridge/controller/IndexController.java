package org.bluebridge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ronin
 * 注意：启动时要使用spring-boot-maven-plugin的springboot:run启动，否则会报404
 */
@Controller
public class IndexController {

    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("msg","I am msg from IndexController!");
        return "hello";
    }
}
