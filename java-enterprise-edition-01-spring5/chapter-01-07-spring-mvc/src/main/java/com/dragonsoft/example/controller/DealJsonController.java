package com.dragonsoft.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author ronin
 * @version V1.0
 * @description
 * @class DealJsonController
 * @date 2019/6/19 20:09
 */
@Controller
public class DealJsonController {

    /**
     * 接收前台传递过来的Json对象
     * @param map
     * @return
     */
    @ResponseBody
    @PostMapping("/dealSignJsonUseJsonObject")
    public Map<String,String> dealSignJsonUseJsonObject(@RequestParam Map<String,String> map){
        System.out.println(map);
        //{name=zhangsan, age=28, win[]=2006}
        return map;
    }

    /**
     * 接收前台传递过来的Json格式的字符串
     * @param map
     * @return
     */
    @ResponseBody
    @PostMapping("/dealSignJsonUseJsonString")
    public Map<String,String> dealSignJsonUseJsonString(@RequestBody Map<String,String> map){
        //如果用@RequestParam接受$.ajax()传递过来的json格式数据,接收到的是空
        System.out.println(map);
        return map;
    }

}
