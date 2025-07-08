package org.bluebridge.ioc.autowiredlocation.test;

import org.bluebridge.ioc.autowiredlocation.config.Config;
import org.bluebridge.ioc.autowiredlocation.dao.UserDao;
import org.bluebridge.ioc.autowiredlocation.controller.UserController;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author lingwh
 * @desc   Autowired注解书写位置测试
 * @date   2019/4/11 10:45
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

