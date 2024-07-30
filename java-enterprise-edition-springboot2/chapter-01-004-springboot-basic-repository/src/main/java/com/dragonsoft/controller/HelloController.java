package com.dragonsoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ronin
 */
@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/repository")
    public String repository(){
        return "springboot repository~[使用阿里云镜像仓库]";
    }

}
