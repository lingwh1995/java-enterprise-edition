package org.bulebridge.ioc.customercomponment.test;

import org.bulebridge.ioc.customercomponment.config.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ronin
 */
public class AwareImplementorTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    }
}
