package org.bluebridge.cookie;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * SpringMVC提供的@CookieValue注解进行解析Cookie中的参数
 */
@RequestMapping(value = "/parseCookie")
@Controller
public class SpringMVCCookieValueAnnoController {

    /**
     * (@CookieValue)参数介绍
     *      defaultValue    默认的请求参数，如果前端不传输值，则使用该值充当前端传来的值
     * @param token
     * @return
     */
    @RequestMapping(value = "/springmvc/cookievalue")
    public String parseCookieBySpringMVCByCookieValueAnno(@CookieValue("token") String token) {
        System.out.println("存储在Cookie中的值- token: " + token);
        return "success";
    }
}
