package org.bluebridge.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author ronin
 * @version V1.0
 * @description SpringMVC restful风格常用的注解
 * @class RestfulAnnotateController
 * @date 2019/6/20 10:17
 */

@Controller
public class RestfulAnnotateController {

    /**
     * 注意：id这个参数被接收了两次,一次是接收为Integer类型的数据,一次是被接收为String类型的数据
     * 使用@PathVariable接收一个参数
     * @param idStr
     * @param idInt
     * @return
     */
    @RequestMapping(value="testpathvariable1/{id}")
    public String testPathVariable1(@PathVariable("id") String idStr, @PathVariable("id") Integer idInt){
        System.out.println("idStr:"+idStr);
        System.out.println("idInt:"+idInt);
        return "success";
    }

    /**
     * 使用@PathVariable接收多个参数
     * @param name
     * @param age
     * @return
     */
    @RequestMapping(value="testpathvariable2/{name}/{age}")
    public String testPathVariable2(@PathVariable("name") String name, @PathVariable("age") Integer age){
        System.out.println("name:"+name);
        System.out.println("age:"+age);
        return "success";
    }

    /**
     * restful api增强:
     * 增加:@PostMapping：url的post请求，相当于@RequestMapping(method= RequestMethod.POST)，但只能用在方法上，不能用在类上，其他参数和RequestMapping用法完全相同。
     * 删除:@DeleteMapping：同PostMapping。在Restful API中代表删除
     * 修改:@PutMapping：同PostMapping。在Restful API中代表更新
     * 查询:@GetMapping：同PostMapping。在Restful API中代表查找
     */

}
