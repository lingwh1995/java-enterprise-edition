package org.bluebridge.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ronin
 */
public class AopNoXmlTest {

    @Test
    public void testAoPNoXml() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Caculator caculator = context.getBean(Caculator.class);
        int result = caculator.div(10, 2);
        System.out.println(result);
    }

}
