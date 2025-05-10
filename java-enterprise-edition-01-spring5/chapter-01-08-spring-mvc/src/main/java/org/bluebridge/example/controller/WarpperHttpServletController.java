package org.bluebridge.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ronin
 * @version V1.0
 * @desc
 * @since 2019/7/26 15:44
 */
@Controller
public class WarpperHttpServletController {

    @RequestMapping("warpperHttpServletTest")
    public String warpperHttpServletTest(HttpServletRequest request){
        System.out.println(request.getParameter("name"));
        return "success";
    }
}
