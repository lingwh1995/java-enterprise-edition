package org.bluebridge.annotation.demo;

import org.bluebridge.annotation.demo.datasource.MyDataSource1;
import org.bluebridge.annotation.demo.user.controller.UserController;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

public class SpringTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringTest.class);

    /**
     * 测试set方式注入经典应用场景，把数据库配置从硬编码中提取到Spring配置文件中
     * @throws SQLException
     */
    @Test
    public void testMyDataSource() throws SQLException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("annotation/demo/applicationContext-di-annotation-demo-datasource.xml");
        MyDataSource1 myDataSource1 = applicationContext.getBean("myDataSource1", MyDataSource1.class);
        myDataSource1.getConnection();
    }

    /**
     * 测试使用setter方式为属性注入引用类型的值  给UserService中的userDao赋值
     * 测试使用构造方式为属性注入引用类型的值     给UserController中的userService赋值
     */
    @Test
    public void testInjectReferenceBySetterAndConstructor(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("annotation/demo/applicationContext-di-annotation-demo-user.xml");
        UserController userController = applicationContext.getBean("userController", UserController.class);
        userController.deleteUserById("001");
    }
}
