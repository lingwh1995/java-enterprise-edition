package org.bluebridge.requestbody;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;

/**
 * 使用SpringMVC提供的原生方式解析请求参数
 */
@RequestMapping(value = "/parseRequestParams")
@Controller
public class SpringMVCNativeController {

    @RequestMapping(value = "/springmvc/native", method = RequestMethod.POST)
    public String parseRequestParamsBySpringMVCByNative(String username,String password,String hobbies,String[] colors) {
        //获取一个key对应一个value的请求参数
        System.out.println("一个key对应一个value的请求参数 - username: " + username + ",password:" + password);
        //获取一个key对应多个value的请求参数
        System.out.println("一个key对应多个value的请求参数 - hobbies: " + hobbies);
        //获取一个key对应多个value的请求参数
        System.out.println("一个key对应多个value的请求参数 - colors: " + Arrays.toString(colors));
        return "success";
    }
}
