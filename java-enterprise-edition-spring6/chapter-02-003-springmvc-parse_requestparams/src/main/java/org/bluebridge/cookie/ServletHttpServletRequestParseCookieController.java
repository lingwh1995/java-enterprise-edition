package org.bluebridge.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

/**
 * 使用Servlet原生api解析Cookie
 */
@RequestMapping(value = "/parseCookie")
@Controller
public class ServletHttpServletRequestParseCookieController {

    @RequestMapping(value = "/servlet/httpServletRequest")
    public String parseCookieByServletByHttpServletRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        System.out.println("存储在Cookie中的值 - token: " + Arrays.toString(cookies));
        return "success";
    }
}
