package org.bluebridge.path;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * SpringMVC提供的@RequestParam注解进行解析请求体中的参数
 *      1.适用于处理请求参数和形参不对应的情况
 *      2.适用于处理可传可不传的请求参数
 */
@RequestMapping(value = "/parseRequestParams")
@Controller
public class SpringMVCPathVariableAnnoController {

    /**
     * 使用@PathVariable注解接收路径中的参数
     * @param id
     * @return
     */
    @RequestMapping(value = "/springmvc/PathVariable/{id}")
    public String parseRequestParamsBySpringMVCByPathVariableAnno(@PathVariable("id") String id) {
        System.out.println("id: " + id);
        return "success";
    }
}
