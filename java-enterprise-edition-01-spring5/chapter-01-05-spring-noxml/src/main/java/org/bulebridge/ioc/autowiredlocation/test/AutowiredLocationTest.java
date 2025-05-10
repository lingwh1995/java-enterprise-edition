package org.bulebridge.ioc.autowiredlocation.test;

import org.bulebridge.ioc.autowiredlocation.config.Config;
import org.bulebridge.ioc.autowiredlocation.controller.UserController;
import org.bulebridge.ioc.autowiredlocation.dao.UserDao;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ronin
 */
public class AutowiredLocationTest {

    @Test
    public void testAutowiredLocation() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        UserController userController = (UserController)context.getBean("userController");
        userController.say();

        /**
         * 测试Config中public UserDao userDao(Dbutils dbutils){}的dbutils这个参数是容器中获取的
         */
        UserDao userDao = (UserDao) context.getBean("userDao2");
        System.out.println(userDao);
        System.out.println(context.getBean("dbutils"));
    }
}

