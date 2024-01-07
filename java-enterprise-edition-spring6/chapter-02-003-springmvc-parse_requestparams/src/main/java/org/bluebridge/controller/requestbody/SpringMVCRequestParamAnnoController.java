package org.bluebridge.controller.requestbody;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * SpringMVC提供的@RequestParam注解进行解析请求体中的参数
 *      1.适用于处理请求参数和形参不对应的情况
 *      2.适用于处理可传可不传的请求参数
 */
@RequestMapping(value = "/parseRequestParams")
@Controller
public class SpringMVCRequestParamAnnoController {

    /**
     * (@RequestParam)参数介绍
     *      required        该请求参数是否必须
     *      defaultValue    默认的请求参数，如果前端不传输值，则使用该值充当前端传来的值
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/springmvc/requestParam")
    public String parseRequestParamsBySpringMVCByRequestParamAnno(
            @RequestParam("user_name") String username,
            @RequestParam("password") String password) {
        System.out.println("username: " + username + ",password:" + password);
        return "success";
    }
}
