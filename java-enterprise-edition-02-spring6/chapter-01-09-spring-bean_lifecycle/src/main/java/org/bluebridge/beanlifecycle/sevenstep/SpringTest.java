package org.bluebridge.beanlifecycle.sevenstep;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    private static final Logger logger = LogManager.getLogger(SpringTest.class);

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

}
