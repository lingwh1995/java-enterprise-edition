package com.dragonsoft.handler;

import com.dragonsoft.service.ISpringAndMVCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpringAndMVCHandler {

    @Autowired
    private ISpringAndMVCService springAndMVCServiceImpl;

    /**
     * 注意:如果Handler想要获取IOC容器中加载的properties属性的值,则需要在SpringMVC容器中注册
     *      properties文件，Handler无法加载SpringIOC容器中properties
     *
     */
    @Value("${jdbc.user}")
    private String username;

    public SpringAndMVCHandler(){
        System.out.println("------------------Handler中构造方法------------------");
        System.out.println("username:"+username);
        System.out.println("SpringAndMVCHandler Contructor......");
        System.out.println("------------------Handler中构造方法------------------");
    }

    @RequestMapping("/testSpringAndMVC")
    public String testSpringAndMVC(){
        System.out.println("------------------Handler中testSpringAndMVC方法------------------");
        System.out.println("username:"+username);
        springAndMVCServiceImpl.save();
        System.out.println("------------------Handler中testSpringAndMVC方法------------------");
        return "success";
    }
}
