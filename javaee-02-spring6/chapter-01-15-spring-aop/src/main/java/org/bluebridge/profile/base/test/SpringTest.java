package org.bluebridge.profile.base.test;

import org.bluebridge.profile.base.controller.UserController;
import org.bluebridge.profile.base.domain.User;
import org.bluebridge.profile.base.service.IUserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    /**
     * 测试在AOP环境中使用getBean()方法
     */
    @Test
    public void testGetBean() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/base/applicationContext-aop-profile-base.xml");
        //特别注意：当使用AOP功能时，getBean()方法的第二个参数必须是接口类型
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        userService.addUser(new User("001","张三",20));
    }

    /**
     * 测试前置通知
     */
    @Test
    public void testBeforeAdviceConfigByXmlUseAopAspectTag() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/base/applicationContext-aop-profile-base.xml");
        UserController userController = applicationContext.getBean("userController", UserController.class);
        userController.addUser(new User("001","张三",20));
    }

    /**
     * 测试后置通知
     */
    @Test
    public void testAfterReturningAdviceConfigByXmlUseAopAspectTag() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/base/applicationContext-aop-profile-base.xml");
        UserController userController = applicationContext.getBean("userController", UserController.class);
        userController.deleteUserById("001");
    }

    /**
     * 测试环绕通知
     */
    @Test
    public void testAroundAdviceConfigByXmlUseAopAspectTag() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/base/applicationContext-aop-profile-base.xml");
        UserController userController = applicationContext.getBean("userController", UserController.class);
        userController.updateUser(new User("001","张三",20));
    }

    /**
     * 测试最终通知
     */
    @Test
    public void testAfterAdviceConfigByXmlUseAopAspectTag() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/base/applicationContext-aop-profile-base.xml");
        UserController userController = applicationContext.getBean("userController", UserController.class);
        userController.getUserById("001");
    }

    /**
     * 测试异常通知
     */
    @Test
    public void testAfterThrowingAdviceConfigByXmlUseAopAspectTag() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/base/applicationContext-aop-profile-base.xml");
        UserController userController = applicationContext.getBean("userController", UserController.class);
        userController.login();
    }

}
