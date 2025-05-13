package org.bluebridge.profile.aspectorder.test;

import org.bluebridge.profile.aspectorder.service.ICatService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    @Test
    public void testAspectOrder() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/aspectorder/applicationContext-aop-profile-aspectorder.xml");
        ICatService catService = applicationContext.getBean("catService", ICatService.class);
        catService.deleteCatById("001");
    }

}
