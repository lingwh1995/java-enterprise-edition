package com.dragonsoft.ioc.autoinject.test;

import com.dragonsoft.ioc.autoinject.config.Config;
import com.dragonsoft.ioc.autoinject.controller.BookController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ronin
 */
public class AutoWiredTest {
    public static void main(String[] args) {
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
