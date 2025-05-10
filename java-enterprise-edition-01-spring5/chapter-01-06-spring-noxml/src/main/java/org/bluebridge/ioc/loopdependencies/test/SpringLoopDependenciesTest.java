package org.bluebridge.ioc.loopdependencies.test;

import org.bluebridge.ioc.loopdependencies.config.Config;
import org.bluebridge.ioc.loopdependencies.service.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 零配置搭建Spring开发环境测试
 * @author ronin
 */
public class SpringLoopDependenciesTest {

    @Test
    public void testSpringLoopDependencies() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        UserService userService = context.getBean("userService", UserService.class);
        userService.getPersonService();
    }

}
