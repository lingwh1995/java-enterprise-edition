package org.bluebridge.controller;

import org.bluebridge.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ResponeBodyController
 *
 * @author lingwh
 * @date 2023/12/14 01:43
 */
@RequestMapping(value = "/httpmessageconverter")
@Controller
public class ResponeBodyController {

    /**
     * 使用 @ResponseBody 以字符串形式给响应浏览器一个字符串(不会把 success 作为视图名称解析了，只会给浏览器返回 success 这个字符串)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/responseBody/string")
    public String responseStringToBrowserByResponseBody() {
        return "测试@ResponseBody注解作用";
    }

    /**
     * 使用 @ResponseBody 以 JSON 字符串形式给响应浏览器一个对象(不会把 success 作为视图名称解析了，只会给浏览器返回 user 这个对象的 json 字符串)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/responseBody/jsonStringFormat/object", produces={"application/json; charset=UTF-8"})
    public User responseJsonStringFormatObjectToBrowserByResponseBody() {
        User user = new User("001", "张三", "123456", "zhangsan@gmail.com");
        return user;
    }

    /**
     * 使用 @ResponseBody 以 Xml 文本形式给响应浏览器一个对象(不会把 success 作为视图名称解析了，只会给浏览器返回 user 这个对象的 xml 文本)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/responseBody/xmlText/object", produces={"application/xml; charset=UTF-8"})
    public User responseXmlTextFormatObjectToBrowserByResponseBody() {
        User user = new User("001", "张三", "123456", "zhangsan@gmail.com");
        return user;
    }
}
