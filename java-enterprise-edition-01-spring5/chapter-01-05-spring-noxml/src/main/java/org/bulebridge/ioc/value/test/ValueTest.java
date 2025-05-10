package org.bulebridge.ioc.value.test;

import org.bulebridge.ioc.value.config.Config;
import org.bulebridge.ioc.value.domain.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author ronin
 */
public class ValueTest {
    public static void main(String[] args) {
        /**
         * 第一种获取配置文件中值的方式,使用SpringEL和@Value注解配合使用
         */
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Person person = context.getBean(Person.class);
        System.out.println(person);

        /**
         * 第二种获取配置文件中值的方式,使用Spring运行环境来获取
         */
        ConfigurableEnvironment environment = context.getEnvironment();
        String school = environment.getProperty("person.school");
        System.out.println(school);
    }
}
