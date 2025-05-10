package org.bulebridge.ioc.autoinject.config;

import org.bulebridge.ioc.autoinject.dao.BookDao;
import org.bulebridge.ioc.autoinject.service.BookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author ronin
 */
@Configuration
@ComponentScan({
        "org.bulebridge.ioc.autoinject.controller",
        "org.bulebridge.ioc.autoinject.service",
        "org.bulebridge.ioc.autoinject.dao",
        "org.bulebridge.ioc.autoinject.dbutils"})
public class Config {
    @Bean(name="bookDao2")
    public BookDao bookDao(){
        BookDao bookDao = new BookDao();
        bookDao.setLabel("被@Bean标注的在config中的bookDao2");
        return bookDao;
    }

    @Primary
    @Bean(name="bookService2")
    public BookService bookService(){
        BookService bookService = new BookService();
        bookService.setLabel("被@Bean标注的在config中的bookService2");
        return bookService;
    }
}
