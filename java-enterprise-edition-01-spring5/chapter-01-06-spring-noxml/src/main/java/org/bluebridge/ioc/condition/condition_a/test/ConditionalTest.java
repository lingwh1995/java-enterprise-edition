package org.bluebridge.ioc.condition.condition_a.test;

import org.bluebridge.ioc.condition.condition_a.config.Config;
import org.bluebridge.ioc.condition.condition_a.domain.Person;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Conditional：
 *  可以写在方法上或者类上
 * @author ronin
 */
public class ConditionalTest {

    @Test
    public void testConditional() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Person person = context.getBean("person", Person.class);
        System.out.println(person);
    }

}
