package org.bluebridge;

import org.bluebridge.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/httpmessageconverter")
@Controller
public class ResponseEntityController {

    /**
     * 使用@ResponseEntity以字符串形式给响应浏览器一个字符串(不会把success作为视图名称解析了，只会给浏览器返回 success 这个字符串)
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/responseEntity/string")
    public ResponseEntity<String> responseStringToBrowserByResponseEntity() {
        String result = "测试@ResponseEntity注解作用";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 使用@ResponseEntity以JSON字符串形式给响应浏览器一个对象(不会把success作为视图名称解析了，只会给浏览器返回 user 这个对象的json字符串)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/responseEntity/jsonStringFormat/object", produces={"application/json; charset=UTF-8"})
    public ResponseEntity<User> responseJsonStringFormatObjectToBrowserByResponseEntity() {
        User user = new User("001", "张三", "123456", "zhangsan@gmail.com");
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    /**
     * 使用@ResponseBody以Xml文本形式给响应浏览器一个对象(不会把success作为视图名称解析了，只会给浏览器返回 user 这个对象的xml文本)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/responseEntity/xmlText/object", produces={"application/xml; charset=UTF-8"})
    public ResponseEntity<User> responseXmlTextFormatObjectToBrowserByResponseBody() {
        User user = new User("001", "张三", "123456", "zhangsan@gmail.com");
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
