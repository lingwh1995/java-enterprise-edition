package org.bluebridge.beanlifecycle.fivestep;

import org.bluebridge.beanlifecycle.sevenstep.SpringBeanLifeCycleSevenStep;
import org.bluebridge.beanlifecycle.tenstep.SpringBeanLifeCycleTenStep;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringTest.class);

    /**
     * 测试Bean的生命周期：五步
     */
    @Test
    public void testSpringBeanLifeCycleFiveStep() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-fivestep.xml");
        SpringBeanLifeCycleFiveStep springBeanLifeCycleFiveStep = applicationContext.getBean("springBeanLifeCycleFiveStep", SpringBeanLifeCycleFiveStep.class);
        logger.info("第四步：使用Bean-" + springBeanLifeCycleFiveStep.toString());
        //手动关闭Spring容器
        ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) applicationContext;
        context.close();
    }

    /**
     * 测试Bean的生命周期：七步
     */
    @Test
    public void testSpringBeanLifeCycleSevenStep() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-sevenstep.xml");
        SpringBeanLifeCycleSevenStep springBeanLifeCycleSevenStep = applicationContext.getBean("springBeanLifeCycleSevenStep", SpringBeanLifeCycleSevenStep.class);
        logger.info("第六步：使用Bean-" + springBeanLifeCycleSevenStep.toString());
        //手动关闭Spring容器
        ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) applicationContext;
        context.close();
    }

    /**
     * 测试Bean的生命周期：十步
     */
    @Test
    public void testSpringBeanLifeCycleTenStep() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-tenstep.xml");
        SpringBeanLifeCycleTenStep springBeanLifeCycleTenStep = applicationContext.getBean("springBeanLifeCycleTenStep", SpringBeanLifeCycleTenStep.class);
        logger.info("第六步：使用Bean-" + springBeanLifeCycleTenStep.toString());
        //手动关闭Spring容器
        ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) applicationContext;
        context.close();
    }
}
