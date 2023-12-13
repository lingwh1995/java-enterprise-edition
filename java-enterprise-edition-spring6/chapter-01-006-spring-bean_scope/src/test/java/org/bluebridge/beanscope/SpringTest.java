package org.bluebridge.beanscope;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {
    private static final Logger logger = LoggerFactory.getLogger(SpringTest.class);

    /**
     * 测试Spring容器默认情况下创建的bean是否为单例bean
     *      1.bean是在Spring容器启动的时候创建的
     *      2.每次调用的getBean()方法时都是去容器中获取创建好的bean
     *      3.默认情况下创建的bean是单例的
     */
    @Test
    public void testSpringBeanDefaultBeanScope(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringBeanScopeDefault springBeanScopeDefault1 = applicationContext.getBean("springBeanScopeDefault", SpringBeanScopeDefault.class);
        logger.info(String.valueOf(springBeanScopeDefault1.hashCode()));
        SpringBeanScopeDefault springBeanScopeDefault2 = applicationContext.getBean("springBeanScopeDefault", SpringBeanScopeDefault.class);
        logger.info(String.valueOf(springBeanScopeDefault2.hashCode()));
        logger.info(String.valueOf(springBeanScopeDefault1 == springBeanScopeDefault2));
    }
    /**
     * bean的scope为singleton
     *      1.bean是在Spring容器启动的时候创建的
     *      2.每次调用的getBean()方法时都是去容器中获取创建好的bean
     *      3.这个作用域下创建的bean是单例的
     */
    @Test
    public void testSpringBeanSingletonBeanScope(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringBeanScopeSingleton springBeanScopeSingleton1 = applicationContext.getBean("springBeanScopeSingleton", SpringBeanScopeSingleton.class);
        logger.info(String.valueOf(springBeanScopeSingleton1.hashCode()));
        SpringBeanScopeSingleton springBeanScopeSingleton2 = applicationContext.getBean("springBeanScopeSingleton", SpringBeanScopeSingleton.class);
        logger.info(String.valueOf(springBeanScopeSingleton2.hashCode()));
        logger.info(String.valueOf(springBeanScopeSingleton2 == springBeanScopeSingleton2));
    }
    /**
     * bean的scope为prototype
     *      1.bean不是在Spring容器启动的时候创建的
     *      2.每次调用的getBean()方法时才会创建一个该bean对象
     *      3.这个作用域下创建的bean是多例的(原型模式)
     */
    @Test
    public void testSpringBeanPrototypeBeanScope(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringBeanScopePrototype springBeanScopePrototype1 = applicationContext.getBean("springBeanScopePrototype", SpringBeanScopePrototype.class);
        logger.info(String.valueOf(springBeanScopePrototype1.hashCode()));
        SpringBeanScopePrototype springBeanScopePrototype2 = applicationContext.getBean("springBeanScopePrototype", SpringBeanScopePrototype.class);
        logger.info(String.valueOf(springBeanScopePrototype2.hashCode()));
        logger.info(String.valueOf(springBeanScopePrototype1 == springBeanScopePrototype2));
    }
}
