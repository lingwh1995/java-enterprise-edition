package com.dragonsoft.controller;

import com.dragonsoft.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/httpmessageconverter")
@Controller
public class ResponeBodyController {

    /**
     * 使用@ResponseBody以字符串形式给响应浏览器一个字符串(不会把success作为视图名称解析了，只会给浏览器返回 success 这个字符串)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/responseBody/string")
    public String responseStringToBrowserByResponseBody() {
        return "测试@ResponseBody注解作用";
    }

    /**
     * 使用@ResponseBody以JSON字符串形式给响应浏览器一个对象(不会把success作为视图名称解析了，只会给浏览器返回 user 这个对象的json字符串)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/responseBody/jsonStringFormat/object", produces={"application/json; charset=UTF-8"})
    public User responseJsonStringFormatObjectToBrowserByResponseBody() {
        User user = new User("001", "张三", "123456", "zhangsan@gmail.com");
        return user;
    }

    /**
     * 使用@ResponseBody以Xml文本形式给响应浏览器一个对象(不会把success作为视图名称解析了，只会给浏览器返回 user 这个对象的xml文本)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/responseBody/xmlText/object", produces={"application/xml; charset=UTF-8"})
    public User responseXmlTextFormatObjectToBrowserByResponseBody() {
        User user = new User("001", "张三", "123456", "zhangsan@gmail.com");
        return user;
    }
}
