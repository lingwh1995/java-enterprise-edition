package org.bluebridge.profile.genericpointcut.test;

import org.bluebridge.profile.genericpointcut.service.IOrderService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    /**
     * 测试配置通用连接点
     */
    @Test
    public void testGenericPointCut() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/genericpointcut/applicationContext-aop-profile-genericpointcut.xml");
        IOrderService orderService = applicationContext.getBean("orderService", IOrderService.class);
        orderService.deleteOrderById("001");
    }
}
