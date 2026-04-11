package org.bluebridge.profile.demo.user.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bluebridge.profile.demo.datasource.MyDataSource1;
import org.bluebridge.profile.demo.user.controller.UserController;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

public class SpringTest {

    private static final Logger logger = LogManager.getLogger(SpringTest.class);

    /**
     * 测试使用setter方式为属性注入引用类型的值  给UserService中的userDao赋值
     * 测试使用构造方式为属性注入引用类型的值     给UserController中的userService赋值
     */
    @Test
    public void testInjectReferenceBySetterAndConstructor(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/demo/applicationContext-di-profile-demo-user.xml");
        UserController userController = applicationContext.getBean("userController", UserController.class);
        userController.deleteUserById("001");
    }
}
