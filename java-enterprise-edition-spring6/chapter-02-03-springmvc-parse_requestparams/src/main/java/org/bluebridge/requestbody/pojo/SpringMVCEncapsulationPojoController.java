package org.bluebridge.requestbody.pojo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 使用SpringMVC提供的封装参数到POJO方式解析请求参数
 */
@RequestMapping(value = "/parseRequestParams")
@Controller
public class SpringMVCEncapsulationPojoController {

    @RequestMapping(value = "/springmvc/encapsulationPojo", method = RequestMethod.POST)
    public String parseRequestParamsBySpringMVCByEncapsulationPojo(User user) {
        System.out.println("使用pojo接收请求参数 - user: " + user);
        return "success";
    }
}
