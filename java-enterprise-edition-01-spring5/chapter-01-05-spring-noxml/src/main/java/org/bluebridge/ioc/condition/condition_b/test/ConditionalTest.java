package org.bluebridge.ioc.condition.condition_b.test;

import org.bluebridge.ioc.condition.condition_b.config.ConditionalAutoConfig;
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
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConditionalAutoConfig.class);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }

}
