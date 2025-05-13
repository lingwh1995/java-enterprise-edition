package org.bluebridge.annotation.demo.test;

import org.bluebridge.annotation.demo.controller.OrderController;
import org.bluebridge.annotation.demo.domain.Order;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    /**
     * 测试基于注解的日志切面类
     */
    @Test
    public void testLogAspectConfigByAnnotation() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("annotation/demo/applicationContext-aop-annotation-demo.xml");
        OrderController orderController = applicationContext.getBean("orderController", OrderController.class);
        orderController.addOrder(new Order("001","28.5"));
        orderController.deleteOrderById("001");
        orderController.updateOrder(new Order("001","28.5"));
        orderController.getOrderById("001");
    }
}
