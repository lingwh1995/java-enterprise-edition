package org.bluebridge.profile.base.constructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringTest {

    private static final Logger logger = LogManager.getLogger(SpringTest.class);

    /**
     * 测试使用构造方式为属性注入非引用数据类型的值，使用name声明具体的属性
     */
    @Test
    public void testInjectValueByConstructorParamName(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/base/applicationContext-di-base.xml");
        UserInjectValueByConstructor user = applicationContext.getBean("userInjectValueByConstructorParamName", UserInjectValueByConstructor.class);
        logger.info(user.toString());
    }

    /**
     * 测试使用构造方式为属性注入非引用数据类型的值，使用name声明具体的属性
     */
    @Test
    public void testInjectValueByConstructorParamIndex(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/base/applicationContext-di-base.xml");
        UserInjectValueByConstructor user = applicationContext.getBean("userInjectValueByConstructorParamIndex", UserInjectValueByConstructor.class);
        logger.info(user.toString());
    }
}
