package org.bulebridge.annotation.genericpointcut;

import org.bluebridge.annotation.genericpointcut.service.IOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    /**
     * 测试配置通用连接点
     */
    @Test
    public void testGenericPointCut() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("annotation/genericpointcut/applicationContext-aop-annotation-genericpointcut.xml");
        IOrderService orderService = applicationContext.getBean("orderService", IOrderService.class);
        orderService.deleteOrderById("001");
    }
}
