package org.bluebridge.profile.autowired;

import org.bluebridge.profile.autowire.byname.service.IOrderService;
import org.bluebridge.profile.autowire.byname.service.OrderServiceImpl;
import org.bluebridge.profile.autowire.bytype.service.IPersonService;
import org.bluebridge.profile.autowire.bytype.service.PersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringTest.class);

    /**
     * 测试使用setter方式为属性注入非引用数据类型的值
     */
    @Test
    public void testInjectValueByAutowiredByNameBySetter(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/autowired/applicationContext-di-profile-autowired-byname.xml");
        IOrderService orderService = applicationContext.getBean("orderService", OrderServiceImpl.class);
        orderService.deleteById("001");
    }

    /**
     * 测试使用setter方式为属性注入非引用数据类型的值
     */
    @Test
    public void testInjectValueByAutowiredByTypeBySetter(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/autowired/applicationContext-di-profile-autowired-bytype.xml");
        IPersonService personService = applicationContext.getBean("personService", PersonServiceImpl.class);
        personService.deleteById("001");
    }

}
