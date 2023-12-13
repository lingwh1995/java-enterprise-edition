package org.bluebridge.profile.base;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringTest.class);

    /**
     * 测试使用setter方式为属性注入非引用数据类型的值
     */
    @Test
    public void testInjectValueBySetter(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/base/applicationContext-di-base.xml");
        UserInjectValueBySetter user = applicationContext.getBean("userInjectValueBySetter", UserInjectValueBySetter.class);
        logger.info(user.toString());
    }

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
