package org.bluebridge.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lingwh
 * @desc
 * @date   2019/6/20 9:08
 */
@Controller
public class InterceptorController {

    @RequestMapping("/testInterceptor1")
    public String testInterceptor1(){
        System.out.println("testInterceptor1......");
        return "success";
    }

    @RequestMapping("/testInterceptor2")
    public String testInterceptor2(){
        System.out.println("testInterceptor2......");
        return "success";
    }

}
