package org.bluebridge.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bluebridge.controller.UserController;
import org.bluebridge.domain.User;
import org.dom4j.DocumentException;
import org.junit.Test;
import org.summerframework.core.BeanFactory;
import org.summerframework.core.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationTargetException;

public class SpringTest {

    private Logger logger = LogManager.getLogger(SpringTest.class);

    @Test
    public void testBeanFactory() throws DocumentException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        BeanFactory applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        //测试当property为简单类型的数据时
        User user = (User) applicationContext.getBean("user");
        logger.info(user.toString());

        //测试当property为引用类型的数据时
        UserController userController = (UserController) applicationContext.getBean("userController");
        userController.deleteUserById("001");
    }

}
