package org.bluebridge.propgation.test;

import org.bluebridge.propgation.service.IAccountService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试Spring事务传播行为
 */
public class SpringTest {

    @Test
    public void testSpringPropgation() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("propgation/applicationContext-tx-declarative-annotation.xml");
        IAccountService accountService = applicationContext.getBean("accountService", IAccountService.class);
        accountService.transfer("张三","李四",100);
    }
}
