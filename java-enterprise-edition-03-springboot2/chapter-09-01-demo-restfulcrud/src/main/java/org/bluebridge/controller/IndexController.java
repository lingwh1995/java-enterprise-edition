package org.bluebridge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 如何使得用户可以访问到templates文件夹下的index.html文件:
 *      方式1:在Controlelr中写下面的代码
 *            @RequestMapping({"/","/index.html"})
 *            public String index(){
 *                return "index";
 *            }
 *            返回值会被thymeleaf接管,会自动找到跳转到templates文件夹下的文件
 *       方式2:继承WebMvcConfigurationAdapter
 *           详细见org.bluebridge.config.IndexViewResolver
 * @author ronin
 */
@Controller
public class IndexController {

//    @RequestMapping({"/","/index.html"})
//    public String index(){
//        return "index";
//    }
}
