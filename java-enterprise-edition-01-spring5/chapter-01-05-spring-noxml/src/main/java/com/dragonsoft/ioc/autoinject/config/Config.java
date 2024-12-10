package com.dragonsoft.ioc.autoinject.config;

import com.dragonsoft.ioc.autoinject.dao.BookDao;
import com.dragonsoft.ioc.autoinject.service.BookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author ronin
 */
@Configuration
@ComponentScan({
        "com.dragonsoft.ioc.autoinject.controller",
        "com.dragonsoft.ioc.autoinject.service",
        "com.dragonsoft.ioc.autoinject.dao",
        "com.dragonsoft.ioc.autoinject.dbutils"})
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
