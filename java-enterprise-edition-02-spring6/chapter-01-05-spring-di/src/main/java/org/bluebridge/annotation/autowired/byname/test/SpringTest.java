package org.bluebridge.annotation.autowired.byname.test;

import org.bluebridge.annotation.autowired.byname.controller.OrderController;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    /**
     * 测试使用@Autowired可以配置的位置
     */
    @Test
    public void testInjectValueAutowiredAndQualifierByNameByAnnotation() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("annotation/autowired/applicationContext-di-annotation-autowired-byname.xml");
        OrderController orderController = applicationContext.getBean("orderController", OrderController.class);
        orderController.deleteById("001");
    }
}
