package org.bulebridge.profile.advisor;

import org.bluebridge.profile.advisor.service.ITeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    @Test
    public void testAopConfigByXmlUseAdvisor() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/advisor/applicationContext-aop-profile-advisor.xml");
        ITeacherService teacherService = applicationContext.getBean("teacherService", ITeacherService.class);
        teacherService.deleteById("001");
    }
}
