package org.bluebridge.annotation.autowired.location.test;

import org.bluebridge.annotation.autowired.location.service.CatServiceImpl;
import org.bluebridge.annotation.autowired.location.service.ICatService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    /**
     * 测试使用@Autowired和@Qualifier按名称注入
     */
    @Test
    public void testAutowiredConfigLocation() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("annotation/autowired/applicationContext-di-annotation-autowired-location.xml");
        ICatService catService = applicationContext.getBean("catServiceImpl", CatServiceImpl.class);
        catService.deleteById("001");
    }

}
