package org.bluebridge.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * SpringMVC 的 RequestMapping 注解
 *
 * 1. 用法类似于 @RequestParam 注解，可以获取所有的请求头信息
 * 2. 也有 required 属性
 *
 * @author lingwh
 * @date 2019/6/20 14:22
 */
@Controller
public class RequestMappingController {

    private static final String SUCCESS = "success";

    /**
     * 简单的 Spring 入门 demo
     *
     * @return
     */
    @RequestMapping(value="hello")
    public String syaHello(){
        System.out.println("Hello World!");
        return SUCCESS;
    }

    /**
     * RequestMapping 注解
     *
     * value：请求 url
     * method：请求方式 GET/POST
     * params：支持简单的表达式，必须有 name 参数而且 age 参数的值不等于 10
     * headers：支持简单的表达式
     *
     * @return
     */
    @RequestMapping(value="testRequestMappping",
                    method= RequestMethod.GET,
                    params = {"username","age!=10"})
    public String testRequestMappping(){
        System.out.println("testRequestMappping!");
        return SUCCESS;
    }
}
