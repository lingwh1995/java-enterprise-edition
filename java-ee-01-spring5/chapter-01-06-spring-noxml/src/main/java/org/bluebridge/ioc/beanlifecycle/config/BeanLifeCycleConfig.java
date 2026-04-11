package org.bluebridge.ioc.beanlifecycle.config;

import org.bluebridge.ioc.beanlifecycle.domain.Car;
import org.bluebridge.ioc.beanlifecycle.domain.Cat;
import org.bluebridge.ioc.beanlifecycle.domain.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/10 11:29
 */
@Configuration
@ComponentScan("org.bluebridge.ioc.beanlifecycle.domain")
public class BeanLifeCycleConfig {

    @Bean(initMethod = "init",destroyMethod ="destory")
    public Car car(){
        return new Car();
    }

    @Bean
    public Cat cat(){
        return new Cat();
    }

    @Bean
    public Dog dog(){
        return new Dog();
    }
}
