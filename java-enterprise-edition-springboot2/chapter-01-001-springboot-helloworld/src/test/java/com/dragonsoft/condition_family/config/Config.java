package com.dragonsoft.condition_family.config;

import com.dragonsoft.condition_family.domain.Dog;
import com.dragonsoft.condition_family.domain.Person;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ronin
 * @version V1.0
 * @since 2019/11/14 10:59
 */
@Configuration
public class Config {

    @Bean
    public Dog dog(){
        return new Dog("小汪","10086");
    }

    /**
     * @ConditionalOnBean
     *      当给定的在bean存在时,则实例化当前Bean
     * @ConditionalOnMissingBean
     *      当给定的在bean不存在时,则实例化当前Bean
     * @ConditionalOnClass
     *      当给定的类名在类路径上存在，则实例化当前Bean
     * @ConditionalOnMissingClass
     *      当给定的类名在类路径上不存在，则实例化当前Bean
     * @return
     */
    @Bean
    @ConditionalOnBean(name = "dog")
    //@ConditionalOnMissingBean(name = "dog")
    public Person person(){
        Person person = new Person();
        person.setName("张三");
        person.setAge(23);
        return person;
    }
}

