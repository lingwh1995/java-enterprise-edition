package org.bluebridge.annotation.resource;

import org.bluebridge.annotation.resource.controller.StudentController;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringTest.class);

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
