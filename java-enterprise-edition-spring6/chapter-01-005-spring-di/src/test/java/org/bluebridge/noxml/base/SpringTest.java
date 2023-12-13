package org.bluebridge.noxml.base;

import org.bluebridge.noxml.base.controller.TeacherController;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringTest.class);

    /**
     * 测试Spring的全注解开发
     */
    @Test
    public void testNoXml() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        TeacherController teacherController = applicationContext.getBean("teacherController", TeacherController.class);
        teacherController.deleteById("001");
    }

}
