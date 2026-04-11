package org.bluebridge.ioc.autoinject.config;

import org.bluebridge.ioc.autoinject.dao.BookDao;
import org.bluebridge.ioc.autoinject.service.BookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/12 12:19
 */
@Configuration
@ComponentScan({
        "org.bluebridge.ioc.autoinject.controller",
        "org.bluebridge.ioc.autoinject.service",
        "org.bluebridge.ioc.autoinject.dao",
        "org.bluebridge.ioc.autoinject.dbutils"})
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
