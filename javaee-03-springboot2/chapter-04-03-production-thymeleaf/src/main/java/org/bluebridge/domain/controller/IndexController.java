package org.bluebridge.domain.controller;

import org.bluebridge.domain.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {

    /**
     * 访问   http://localhost:8080/index 进行测试
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("company","西安巴比特");
        User user = new User("001", "张三", "123456");
        model.addAttribute("user",user);
        return "index";
    }
}
