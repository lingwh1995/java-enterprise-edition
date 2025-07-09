package org.bluebridge.mvcsuperior.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingwh
 * @desc   SpringMVC高级特性
 * @date   2019/7/8 13:32
 */
@Controller
public class SpringMVCSuperior {

    /**
     * WebRequest是Spring WebMVC提供的统一请求访问接口，不仅仅可以访问请求相关数据（如参数区数据、请求头数据，但访问不到Cookie区数据），还
     * 可以访问会话和上下文中的数据；NativeWebRequest继承了WebRequest，并提供访问本地ServletAPI的方法。
     * @param webRequest
     * @return
     */
    @RequestMapping("testwebrequest")
    public String webRequest(WebRequest webRequest, NativeWebRequest nativeWebRequest) {
        /**
         * webRequest.getParameter：访问请求参数区的数据，可以通过getHeader()访问请求头数据；
         */
        System.out.println(webRequest.getParameter("test"));
        /**
          * 2.webRequest.setAttribute/getAttribute：到指定的作用范围内取/放属性数据，Servlet定义的三个作用范围分别使用如下常量代表：
          *       SCOPE_REQUEST ：代表请求作用范围
          *       SCOPE_SESSION ：代表会话作用范围
          *       SCOPE_GLOBAL_SESSION ：代表全局会话作用范围，即ServletContext上下文作用范围
         */
        webRequest.setAttribute("name", "value",WebRequest. SCOPE_REQUEST);
        System. out.println(webRequest.getAttribute("name",WebRequest. SCOPE_REQUEST));
        /**
         * nativeWebRequest.getNativeRequest/nativeWebRequest.getNativeResponse：得到本地ServletAPI
         */
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        return "success";
    }

}
