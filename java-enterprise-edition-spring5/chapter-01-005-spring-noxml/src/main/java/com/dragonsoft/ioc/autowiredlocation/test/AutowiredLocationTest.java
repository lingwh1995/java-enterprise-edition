package com.dragonsoft.ioc.autowiredlocation.test;

import com.dragonsoft.ioc.autowiredlocation.config.Config;
import com.dragonsoft.ioc.autowiredlocation.controller.UserController;
import com.dragonsoft.ioc.autowiredlocation.dao.UserDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ronin
 */
public class AutowiredLocationTest {
    public static void main(String[] args) {
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

