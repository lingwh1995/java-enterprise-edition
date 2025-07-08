package org.bluebridge.ioc.customercomponment.test;

import org.bluebridge.ioc.customercomponment.config.Config;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/6 13:47
 */
public class AwareImplementorTest {

    @Test
    public void testAwareImplementor() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    }

}
