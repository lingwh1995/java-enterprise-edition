package org.bluebridge.ioc.hellowrold.test;

import org.bluebridge.ioc.hellowrold.config.Config;
import org.bluebridge.ioc.hellowrold.domain.Person;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * 零配置搭建Spring开发环境测试
 * @author ronin
 */
public class ApplicationContextHasnoXmlTest {

    @Autowired
    private Person person;

    @Test
    public void fun1(){
        System.out.println(person);
    }

    @Test
    public void fun2(){
        //构造参数传入Config.class，即配置类的class
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        System.out.println("------------------IOC容器初始化完成---------------------------");
        Person person = context.getBean("person",Person.class);
        System.out.println(person);
    }

    @Test
    public void fun3(){
        //构造参数传入Config.class，即配置类的class
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }

}
