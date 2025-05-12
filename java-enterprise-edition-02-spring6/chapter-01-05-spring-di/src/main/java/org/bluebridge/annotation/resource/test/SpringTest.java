package org.bluebridge.annotation.resource.test;

import org.bluebridge.annotation.resource.controller.StudentController;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    /**
     * 测试使用@Resource完成di
     */
    @Test
    public void testResource() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("annotation/resource/applicationContext-di-annotation-resource.xml");
        StudentController studentController = applicationContext.getBean("studentController", StudentController.class);
        studentController.deleteById("001");
    }
}
