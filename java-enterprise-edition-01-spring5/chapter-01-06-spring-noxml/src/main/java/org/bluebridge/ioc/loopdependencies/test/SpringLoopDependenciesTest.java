package org.bluebridge.ioc.loopdependencies.test;

import org.bluebridge.ioc.loopdependencies.config.Config;
import org.bluebridge.ioc.loopdependencies.service.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author lingwh
 * @desc   Spring循环依赖
 * @date   2019/4/4 10:46
 */
public class SpringLoopDependenciesTest {

    @Test
    public void testSpringLoopDependencies() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        UserService userService = context.getBean("userService", UserService.class);
        userService.getPersonService();
    }

}
