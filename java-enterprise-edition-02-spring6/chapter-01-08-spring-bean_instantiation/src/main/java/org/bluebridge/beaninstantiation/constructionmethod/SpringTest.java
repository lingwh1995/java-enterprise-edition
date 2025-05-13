package org.bluebridge.beaninstantiation.constructionmethod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bluebridge.beaninstantiation.factorybeaninterface.Tank;
import org.bluebridge.beaninstantiation.factorybeaninterface.demo.User;
import org.bluebridge.beaninstantiation.factorymethod.Gun;
import org.bluebridge.beaninstantiation.simplefactory.Car;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    private static final Logger logger = LogManager.getLogger(SpringTest.class);

    /**
     * 测试Spring示例化bean的第一种方式： 使用构造方法实例化bean
     */
    @Test
    public void testBeanInstantiationByConstructionMethod() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringBean springBean = applicationContext.getBean("springBean", SpringBean.class);
        logger.info(springBean.toString());
    }

}
