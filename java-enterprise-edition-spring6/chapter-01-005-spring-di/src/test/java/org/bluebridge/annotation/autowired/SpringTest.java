package org.bluebridge.annotation.autowired;

import org.bluebridge.annotation.autowired.byname.controller.OrderController;
import org.bluebridge.annotation.autowired.bytype.controller.UserController;
import org.bluebridge.annotation.autowired.location.CatServiceImpl;
import org.bluebridge.annotation.autowired.location.ICatService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    /**
     * 测试使用@Autowired完成按类型注入
     */
    @Test
    public void testInjectValueByAutowiredByTypeByAnnotation() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("annotation/autowired/applicationContext-di-annotation-autowired-bytype.xml");
        UserController userController = applicationContext.getBean("userController", UserController.class);
        userController.deleteById("001");
    }

    /**
     * 测试使用@Autowired和@Qualifier按名称注入
     */
    @Test
    public void testAutowiredConfigLocation() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("annotation/autowired/applicationContext-di-annotation-autowired-location.xml");
        ICatService catService = applicationContext.getBean("catServiceImpl", CatServiceImpl.class);
        catService.deleteById("001");
    }

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
