package org.bluebridge.aop;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ronin
 */
public class AoPTest {
    @Test
    public void testAoP() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Caculator caculator = context.getBean(Caculator.class);
        int result = caculator.div(10, 2);
        System.out.println(result);

        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}
