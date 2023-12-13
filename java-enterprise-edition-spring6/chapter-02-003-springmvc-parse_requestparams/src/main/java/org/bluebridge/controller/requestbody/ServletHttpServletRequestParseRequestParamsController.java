package org.bluebridge.controller.requestbody;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * 使用Servlet原生api解析请求参数
 */
@RequestMapping(value = "/parseRequestParams")
@Controller
public class ServletHttpServletRequestParseRequestParamsController {

    @RequestMapping(value = "/servlet/httpServletRequest")
    public String parseRequestParamsByServletByHttpServletRequest(HttpServletRequest request) {
        //获取一个key对应一个value的请求参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("一个key对应一个value的请求参数 - username: " + username + ",password:" + password);

        //获取一个key对应多个value的请求参数
        String[] hobbies = request.getParameterValues("hobbies");
        System.out.println("一个key对应多个value的请求参数 - hobbies: " + Arrays.toString(hobbies));

        //获取一个key对应多个value的请求参数
        Map<String, String[]> parameters = request.getParameterMap();
        Iterator<Map.Entry<String, String[]>> iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String[]> entry = iterator.next();
            System.out.println("一次性获取全部请求参数: " + entry.getKey() + ":" + Arrays.toString(entry.getValue()));
        }
        return "success";
    }
}
