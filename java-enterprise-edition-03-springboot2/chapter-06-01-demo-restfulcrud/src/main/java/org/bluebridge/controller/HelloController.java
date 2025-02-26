package org.bluebridge.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;

/**
 * @author ronin
 */
@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/hello")
    public String helloWorld(){
        return "hello,world!";
    }

    @RequestMapping("/success")
    public String success(Map<String,Object> map){
        map.put("msg","<h1>这是后台传递的数据！</h2>");
        map.put("users", Arrays.asList("zhangsan","lisi","wangwui"));
        return "success";
    }

}
