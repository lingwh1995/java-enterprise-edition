package org.bluebridge.noxml.base.test;

import org.bluebridge.noxml.base.config.SpringConfig;
import org.bluebridge.noxml.base.controller.TeacherController;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringTest {

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
