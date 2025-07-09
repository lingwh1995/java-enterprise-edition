package org.bluebridge.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lingwh
 * @desc   SpringMVC高级特性
 * @date   2019/7/26 15:44
 */
@Controller
public class WarpperHttpServletController {

    @RequestMapping("warpperHttpServletTest")
    public String warpperHttpServletTest(HttpServletRequest request){
        System.out.println(request.getParameter("name"));
        return "success";
    }
}
