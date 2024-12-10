package org.bulebridge.profile.demo;

import org.bluebridge.profile.demo.domain.Order;
import org.bluebridge.profile.demo.controller.OrderController;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    /**
     * 测试基于XML配置的日志切面类
     */
    @Test
    public void testLogAspectConfigByXml() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/demo/applicationContext-aop-profile-demo.xml");
        OrderController orderController = applicationContext.getBean("orderController", OrderController.class);
        orderController.addOrder(new Order("001","28.5"));
        orderController.deleteOrderById("001");
        orderController.updateOrder(new Order("001","28.5"));
        orderController.getOrderById("001");
    }
}
