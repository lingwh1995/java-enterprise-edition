package com.dragonsoft.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 使用ant风格通配符实现通用的逻辑视图跳转控制器
 *      此类是：Springmvc Ant 风格匹配url
 *          ? 匹配一个任意的字符
 *          * 匹配任意字符
 *          ** 匹配多层任意字符
 */
@Controller
public class PageJumpAndAntController {

    @RequestMapping(value="/a/{page}",method = RequestMethod.GET)
    public String page(@PathVariable("page") String page){
        System.out.println("路径:"+page);
        return page.replace(".action","");
    }
}
