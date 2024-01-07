package com.dragonsoft.ioc.beanlifecycle.config;

import com.dragonsoft.ioc.beanlifecycle.domain.Car;
import com.dragonsoft.ioc.beanlifecycle.domain.Cat;
import com.dragonsoft.ioc.beanlifecycle.domain.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ronin
 */
@Configuration
@ComponentScan("com.dragonsoft.ioc.beanlifecycle.domain")
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
