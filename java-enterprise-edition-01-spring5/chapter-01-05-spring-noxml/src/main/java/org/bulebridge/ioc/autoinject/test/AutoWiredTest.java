package org.bulebridge.ioc.autoinject.test;

import org.bulebridge.ioc.autoinject.config.Config;
import org.bulebridge.ioc.autoinject.controller.BookController;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ronin
 */
public class AutoWiredTest {

    @Test
    public void testAutoWired() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
        System.out.println("------------------------------------------------------------------");
        BookController bookController = (BookController)context.getBean("bookController");
        System.out.println(bookController);
        bookController.say();
    }
}
