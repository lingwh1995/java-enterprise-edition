package org.bulebridge.annotation.aspectorder;

import org.bluebridge.annotation.aspectorder.service.ICatService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试全注解开发Spring AOP
 *  注意  以下测试方法随意执行一个就可以，所有的增强通知都应用在同一个方法上，所以运行任何一个方法的效果都是相同的
 */
public class SpringTest {

    @Test
    public void testAspectOrder() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("annotation/aspectorder/applicationContext-aop-annotation-aspectorder.xml");
        ICatService catService = applicationContext.getBean("catService", ICatService.class);
        catService.deleteCatById("001");
    }
}
