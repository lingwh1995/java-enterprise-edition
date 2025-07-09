package org.bluebridge.mvcsuperior.controller;

import org.bluebridge.mvcsuperior.service.IRequestContextHolderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingwh
 * @desc   SpringMVC高级特性
 * @date   2019/7/23 11:05
 */
@Controller
public class RequestContextHolderController {

    @Resource
    private IRequestContextHolderService requestContextHolderService;

    /**
     * 测试在Service层获取HttpServletRequest/HttpServletResponse对象
     * @return
     */
    @RequestMapping("/testServiceHttpServletRequest")
    public String testServiceHttpServletRequest(){
        //获取HttpServletRequest对象
        HttpServletRequest request = requestContextHolderService.getHttpServletRequest();
        System.out.println(request.getRequestURI());
        //获取HttpServletResponse对象
        HttpServletResponse response = requestContextHolderService.getHttpServletResponse();
        System.out.println(response);
        return "success";
    }
}
