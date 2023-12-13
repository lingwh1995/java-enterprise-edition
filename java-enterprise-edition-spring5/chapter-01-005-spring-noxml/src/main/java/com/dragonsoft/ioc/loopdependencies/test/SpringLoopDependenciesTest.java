package com.dragonsoft.ioc.loopdependencies.test;

import com.dragonsoft.ioc.loopdependencies.config.Config;
import com.dragonsoft.ioc.loopdependencies.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 零配置搭建Spring开发环境测试
 * @author ronin
 */
public class SpringLoopDependenciesTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        UserService userService = context.getBean("userService", UserService.class);
        userService.getPersonService();
    }
}
