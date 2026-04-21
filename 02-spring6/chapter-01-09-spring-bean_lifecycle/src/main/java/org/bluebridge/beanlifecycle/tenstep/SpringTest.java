package org.bluebridge.beanlifecycle.tenstep;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    private static final Logger logger = LogManager.getLogger(SpringTest.class);

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
