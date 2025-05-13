package org.bluebridge.noxml.demo.test;

import org.bluebridge.noxml.demo.config.SpringConfig;
import org.bluebridge.noxml.demo.controller.OrderController;
import org.bluebridge.noxml.demo.domain.Order;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringTest {

    /**
     * 测试基于全注解开发的日志切面类
     */
    @Test
    public void testLogAspectConfigByNoxml() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        OrderController orderController = applicationContext.getBean("orderController", OrderController.class);
        orderController.addOrder(new Order("001","28.5"));
        orderController.deleteOrderById("001");
        orderController.updateOrder(new Order("001","28.5"));
        orderController.getOrderById("001");
    }
}
