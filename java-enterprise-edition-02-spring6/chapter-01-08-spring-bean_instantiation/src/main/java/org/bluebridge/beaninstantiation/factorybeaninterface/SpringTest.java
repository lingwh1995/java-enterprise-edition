package org.bluebridge.beaninstantiation.factorybeaninterface;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bluebridge.beaninstantiation.constructionmethod.SpringBean;
import org.bluebridge.beaninstantiation.factorybeaninterface.demo.User;
import org.bluebridge.beaninstantiation.factorymethod.Gun;
import org.bluebridge.beaninstantiation.simplefactory.Car;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    private static final Logger logger = LogManager.getLogger(SpringTest.class);

    /**
     * 测试Spring示例化bean的第四种方式： 通过实现FactoryBean接口来实例化bean，这种方式是第三种方式的简化，只不过之前工厂模式中的抽象类由Spring框架提供的FactoryBean代替了
     */
    @Test
    public void testBeanInstantiationByFactoryBean() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Tank tank = applicationContext.getBean("tank", Tank.class);
        logger.info(tank.toString());
    }

    /**
     * 测试Spring示例化bean的第四种方式的实际应用： 为属性注入Date类型的值
     */
    @Test
    public void testBeanInstantiationByFactoryBeanInjectDate() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = applicationContext.getBean("user", User.class);
        logger.info(user.toString());
    }
}
