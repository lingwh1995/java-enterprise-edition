package com.dragonsoft.ioc.condition.condition_a.config;


import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;


/**
 * @author ronin
 */
public class WindowsConditional implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        System.out.println("--------------------开始使用@Conditional注解实现管理IOC容器中bean的注册情况------------------------------");
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        ClassLoader classLoader = context.getClassLoader();
        Environment environment = context.getEnvironment();
        //获取bean定义的注册类,可以在这个变量里面查询某个类是不是被注册了,也可以给容器中注册bean
        BeanDefinitionRegistry registry = context.getRegistry();
        String osname = environment.getProperty("os.name");
        if(osname.contains("Windows")){
            System.out.println(osname);
            return true;
        }
        System.out.println("--------------------结束使用@Conditional注解实现管理IOC容器中bean的注册情况------------------------------");
        return false;
    }
}
