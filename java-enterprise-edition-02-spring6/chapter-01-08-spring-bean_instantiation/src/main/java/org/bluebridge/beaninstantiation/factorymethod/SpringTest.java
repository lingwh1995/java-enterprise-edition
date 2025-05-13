package org.bluebridge.beaninstantiation.factorymethod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bluebridge.beaninstantiation.constructionmethod.SpringBean;
import org.bluebridge.beaninstantiation.factorybeaninterface.Tank;
import org.bluebridge.beaninstantiation.factorybeaninterface.demo.User;
import org.bluebridge.beaninstantiation.simplefactory.Car;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    private static final Logger logger = LogManager.getLogger(SpringTest.class);

    /**
     * 测试Spring示例化bean的第三种方式： 通过工厂方法模式实例化bean
     */
    @Test
    public void testBeanInstantiationByFactoryMethod() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Gun gun = applicationContext.getBean("gun", Gun.class);
        logger.info(gun.toString());
    }

}
