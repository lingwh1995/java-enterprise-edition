package com.dragonsoft.converter.handler;

import com.dragonsoft.converter.entity.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @author ronin
 * @version V1.0
 * @description 测试日期转换器
 * @class ConverterHandler
 * @date 2019/6/17 14:32
 */
@Controller
public class ConverterHandler {

    /**
     * 使用自定义的DateConverter将字符串类型的日期转换为java.util.Date的日期
     * @param date
     * @return
     */
    @RequestMapping("/testConvererStringToDate")
    public String converterStringToDate(@RequestParam("time") Date date){
        System.out.println(date);
        return "success";
    }

    /**
     * 使用自定义的DateConverter将字符串类型的日期转换为java.util.Date的日期
     * @param employee  前台输入:1-张三-123456-18
     * @return
     */
    @RequestMapping("/testConvererStringToEmployee")
    public String converterStringToEmplyee(@RequestParam("employee") Employee employee){
        System.out.println(employee);
        return "success";
    }
}
