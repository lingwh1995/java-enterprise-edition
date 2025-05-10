package org.bulebridge.ioc.profile.test;

import org.bulebridge.ioc.profile.config.MainConfigProfile;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 如何切换配置环境:
 *      1.在运行时给动态的给JVM配置参数-Dspring.profiles.active=test
 *      2.在代码层面进行控制
 *      3.@Profile写在配置类上,标注哪个是要用到的环境
 *          前提是:context.getEnvironment().setActiveProfiles("test");里面已经把环境设置进去了
 * @author ronin
 */
public class ProfileTest {
    public static void main(String[] args) {
        //1.创建AnnotationConfigApplicationContext对象
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //2.设置需要激活的环境
        //context.getEnvironment().setActiveProfiles("test","default");
        context.getEnvironment().setActiveProfiles("test");
        //3.设置主配置环境
        context.register(MainConfigProfile.class);
        //4.启动刷新程序
        context.refresh();
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}
