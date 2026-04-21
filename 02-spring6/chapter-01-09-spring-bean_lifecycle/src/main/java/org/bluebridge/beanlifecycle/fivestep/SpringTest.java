package org.bluebridge.beanlifecycle.fivestep;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    private static final Logger logger = LogManager.getLogger(SpringTest.class);

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

}
