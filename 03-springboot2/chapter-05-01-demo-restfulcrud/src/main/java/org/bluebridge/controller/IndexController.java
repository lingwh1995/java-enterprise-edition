package org.bluebridge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 如何使得用户可以访问到 templates 文件夹下的 index.html 文件：
 *      方式1：在 Controlelr 中写下面的代码
 *            @RequestMapping({"/","/index.html"})
 *            public String index(){
 *                return "index";
 *            }
 *            返回值会被 thymeleaf 接管，会自动找到跳转到 templates 文件夹下的文件
 *       方式2：继承 WebMvcConfigurationAdapter
 *           详细见 org.bluebridge.config.IndexViewResolver
 *
 * @author lingwh
 * @date 2024/11/15 11:42
 */
@Controller
public class IndexController {

//    @RequestMapping({"/"，"/index.html"})
//    public String index(){
//        return "index";
//    }
}
