package org.bluebridge.circulardependence.injectbyconstruction;

import org.bluebridge.circulardependence.injectbyconstruction.singleton.Student;
import org.bluebridge.circulardependence.injectbyconstruction.singleton.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {


    /**
     * 测试 singleton + 构造模式
     * 结论：会出现循环依赖问题
     */
    @Test
    public void testSingletonScopeAndConstructionCircularDependence() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("injectbyconstruction/singleton/applicationContext.xml");
        Teacher teacher = applicationContext.getBean("teacher", Teacher.class);
        Student student = applicationContext.getBean("student", Student.class);
    }
}
