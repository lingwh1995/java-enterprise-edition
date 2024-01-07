package com.dragonsoft.ioc.customercomponment.test;

import com.dragonsoft.ioc.customercomponment.config.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ronin
 */
public class AwareImplementorTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    }
}
