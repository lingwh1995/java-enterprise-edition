package org.bluebridge.ioc._import.test;

import org.bluebridge.ioc._import.config.ImportConfig;
import org.bluebridge.ioc._import.config.ImportSelectorConfig;
import org.bluebridge.ioc._import.domain.Blue;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ronin
 */
public class ImportTest {

    @Test
    public void fun1(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ImportConfig.class);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }

    @Test
    public void fun2(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ImportSelectorConfig.class);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
        Blue bean = context.getBean(Blue.class);
        System.out.println(bean);
    }
}
