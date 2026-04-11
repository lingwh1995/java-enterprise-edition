package org.bluebridge.requestheader;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * SpringMVC提供的@RequestHeader注解进行解析请求头中的参数
 */
@RequestMapping(value = "/parseRequestHeader")
@Controller
public class SpringMVCRequesHeaderAnnoController {

    /**
     * (@RequestHeader)参数介绍
     *      defaultValue    默认的请求参数，如果前端不传输值，则使用该值充当前端传来的值
     * @param host
     * @return
     */
    @RequestMapping(value = "/springmvc/requestHeader")
    public String parseRequestHeaderBySpringMVCByRequestHeaderAnno(@RequestHeader("Host") String host) {
        System.out.println("存储在Header中的值 - host: " + host);
        return "success";
    }
}
