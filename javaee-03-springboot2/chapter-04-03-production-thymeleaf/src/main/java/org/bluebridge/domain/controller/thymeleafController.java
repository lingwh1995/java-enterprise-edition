package org.bluebridge.domain.controller;

import org.bluebridge.domain.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class thymeleafController {

    /**
     * 测试th:method和th:action
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String thMethodAndThActionTest(User user, Model model) {
        System.out.println("前台传递的参数user:" + user);
        model.addAttribute("user",user);
        return "success";
    }
}
