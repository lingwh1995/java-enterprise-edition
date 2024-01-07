package org.bluebridge.controller.requestheader;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 使用Servlet原生api解析Cookie
 */
@RequestMapping(value = "/parseRequestHeader")
@Controller
public class ServletHttpServletRequestParseRequestHeaderController {

    @RequestMapping(value = "/servlet/httpServletRequest")
    public String parseRequestHeaderByServletByHttpServletRequest(HttpServletRequest request) {
        String host = request.getHeader("Host");
        System.out.println("存储在Header中的值 - host: " + host);
        return "success";
    }
}
