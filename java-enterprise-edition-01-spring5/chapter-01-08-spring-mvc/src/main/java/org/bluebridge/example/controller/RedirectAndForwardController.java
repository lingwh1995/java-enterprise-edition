package org.bluebridge.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lingwh
 * @desc   SpringMVC转发和重定向: 注意:返回的逻辑视图要加前后缀，否则页面会报404
 * @date   2019/6/20 14:37
 */
@Controller
public class RedirectAndForwardController {

    /**
     * 重定向
     * @return
     */
    @RequestMapping(value="testForward")
    public String testForward(){
        System.out.println("testForward...");
        return "forward:/views/success.jsp";
    }

    /**
     * 转发
     * @return
     */
    @RequestMapping(value="testRedirect")
    public String testRedirect(){
        System.out.println("testRedirect...");
        return "redirect:/views/success.jsp";
    }
}
