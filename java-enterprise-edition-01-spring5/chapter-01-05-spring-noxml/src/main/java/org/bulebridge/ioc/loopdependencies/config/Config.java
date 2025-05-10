package org.bulebridge.ioc.loopdependencies.config;

import org.bulebridge.ioc.loopdependencies.domain.Person;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * @author ronin
 */
@Configuration
@ComponentScan(value= "com.dragonsoft.ioc.loopdependencies",
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,
                classes = {Controller.class, Service.class})})
public class Config {

    /**
     * 默认：
     *      1.默认使用方法名作为id
     *      2.默认创建的bean是单例的
     * 也可以使用@Bean的name属性来指定id
     * @Scope：
     *      如果为单例:IOC容器初始化的过程会实例化bean
     *      如果为多例:IOC容器初始化的过程不会实例化bea
     * @return
     */
    @Scope("prototype")
    @Bean(name="person")
    public Person person(){
        System.out.println("给IOC容器中放值");
        return new Person("0001","zhangsan","29");
    }
}
