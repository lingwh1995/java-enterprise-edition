package org.bluebridge.controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/interceptor")
@Controller
public class InterceptorController {

    private static final Logger logger = LoggerFactory.getLogger(InterceptorController.class);

    /**
     * 测试拦截器   对/interceptor/executeInterceptor 访问这个路径拦截器会生效
     * @return
     */
    @RequestMapping(value = "/executeInterceptor")
    public String executeInterceptor() {
        logger.info("这个方法执行时拦截器会执行拦截操作...");
        return "success";
    }

    /**
     * 测试拦截器   对/interceptor/notExecuteInterceptor 访问这个路径拦截器会不做任何操作
     * @return
     */
    @RequestMapping(value = "/notExecuteInterceptor")
    public String notExecuteInterceptor() {
        logger.info("这个方法执行时拦截器会执行放行操作...");
        return "success";
    }

}
