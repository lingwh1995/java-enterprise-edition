package org.bluebridge.beaninstantiation;

import org.bluebridge.beaninstantiation.constructionmethod.SpringBean;
import org.bluebridge.beaninstantiation.factorybeaninterface.Tank;
import org.bluebridge.beaninstantiation.factorybeaninterface.demo.User;
import org.bluebridge.beaninstantiation.factorymethod.Gun;
import org.bluebridge.beaninstantiation.simplefactory.Car;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringTest.class);

    /**
     * 测试Spring示例化bean的第一种方式： 使用构造方法实例化bean
     */
    @Test
    public void testBeanInstantiationByConstructionMethod() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringBean springBean = applicationContext.getBean("springBean", SpringBean.class);
        logger.info(springBean.toString());
    }

    /**
     * 测试Spring示例化bean的第二种方式： 使用简单(静态)工厂实例化bean
     */
    @Test
    public void testBeanInstantiationBySimpleFactory() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Car car = applicationContext.getBean("car", Car.class);
        logger.info(car.toString());
    }

    /**
     * 测试Spring示例化bean的第三种方式： 通过工厂方法模式实例化bean
     */
    @Test
    public void testBeanInstantiationByFactoryMethod() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Gun gun = applicationContext.getBean("gun", Gun.class);
        logger.info(gun.toString());
    }

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
