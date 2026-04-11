package org.bluebridge.beaninstantiation.simplefactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bluebridge.beaninstantiation.constructionmethod.SpringBean;
import org.bluebridge.beaninstantiation.factorybeaninterface.Tank;
import org.bluebridge.beaninstantiation.factorybeaninterface.demo.User;
import org.bluebridge.beaninstantiation.factorymethod.Gun;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    private static final Logger logger = LogManager.getLogger(SpringTest.class);

    /**
     * 测试Spring示例化bean的第二种方式： 使用简单(静态)工厂实例化bean
     */
    @Test
    public void testBeanInstantiationBySimpleFactory() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Car car = applicationContext.getBean("car", Car.class);
        logger.info(car.toString());
    }

}
