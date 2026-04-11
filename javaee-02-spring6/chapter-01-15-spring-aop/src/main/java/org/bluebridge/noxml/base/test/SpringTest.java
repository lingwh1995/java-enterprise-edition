package org.bluebridge.noxml.base.test;

import org.bluebridge.noxml.base.config.SpringConfig;
import org.bluebridge.noxml.base.service.IStudentService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringTest {

    /**
     * 测试在AOP环境中使用getBean()方法
     */
    @Test
    public void testGetBean() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        //特别注意：当使用AOP功能时，getBean()方法的第二个参数必须是接口类型
        IStudentService studentService = applicationContext.getBean("studentService", IStudentService.class);
        studentService.deleteStudentById("001");
    }

    /**
     * 测试前置通知
     */
    @Test
    public void testBeforeAdvice() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        IStudentService studentService = applicationContext.getBean("studentService", IStudentService.class);
        studentService.deleteStudentById("001");
    }

    /**
     * 测试后置通知
     */
    @Test
    public void testAfterReturningAdvice() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        IStudentService studentService = applicationContext.getBean("studentService", IStudentService.class);
        studentService.deleteStudentById("001");
    }

    /**
     * 测试环绕通知
     */
    @Test
    public void testAroundAdvice() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        IStudentService studentService = applicationContext.getBean("studentService", IStudentService.class);
        studentService.deleteStudentById("001");
    }

    /**
     * 测试最终通知
     */
    @Test
    public void testAfterAdvice() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        IStudentService studentService = applicationContext.getBean("studentService", IStudentService.class);
        studentService.deleteStudentById("001");
    }

    /**
     * 测试异常通知
     */
    @Test
    public void testAfterThrowingAdvice() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        IStudentService studentService = applicationContext.getBean("studentService", IStudentService.class);
        studentService.deleteStudentById("001");
    }
}
