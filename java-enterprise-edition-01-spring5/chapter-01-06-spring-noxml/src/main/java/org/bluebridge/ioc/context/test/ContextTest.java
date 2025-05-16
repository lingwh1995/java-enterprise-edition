package org.bluebridge.ioc.context.test;

import org.bluebridge.ioc.context.config.Config;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ContextTest {

    @Test
    public void testApplicationContext(){
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        // 获取ioc容器中的所有bean的名称
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }

        // 判断ioc容器中时候有user这个bean
        boolean hasBeanUser = context.containsBean("user");
        System.out.println("hasBeanUser = " + hasBeanUser);
    }

}
