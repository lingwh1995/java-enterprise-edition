package org.bluebridge.profile.autowire.byname.test;

import org.bluebridge.profile.autowire.byname.service.IOrderService;
import org.bluebridge.profile.autowire.byname.service.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringTest {

    private static final Logger logger = LogManager.getLogger(SpringTest.class);

    /**
     * 测试使用setter方式为属性注入非引用数据类型的值
     */
    @Test
    public void testInjectValueByAutowiredByNameBySetter(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/autowired/applicationContext-di-profile-autowired-byname.xml");
        IOrderService orderService = applicationContext.getBean("orderService", OrderServiceImpl.class);
        orderService.deleteById("001");
    }

}
