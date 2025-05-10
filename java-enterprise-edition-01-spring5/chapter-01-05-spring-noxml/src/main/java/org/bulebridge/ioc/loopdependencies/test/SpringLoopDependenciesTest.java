package org.bulebridge.ioc.loopdependencies.test;

import org.bulebridge.ioc.loopdependencies.config.Config;
import org.bulebridge.ioc.loopdependencies.service.UserService;
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
