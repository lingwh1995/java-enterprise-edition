package org.bluebridge.ioc.autoinject.test;

import org.bluebridge.ioc.autoinject.config.Config;
import org.bluebridge.ioc.autoinject.controller.BookController;
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
