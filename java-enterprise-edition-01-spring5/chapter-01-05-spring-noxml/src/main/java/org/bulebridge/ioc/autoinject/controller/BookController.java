package org.bulebridge.ioc.autoinject.controller;

import org.bulebridge.ioc.autoinject.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author ronin
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    public void say(){
        System.out.println("----------------------------------------------");
        System.out.println("BookController中注入的BookService是:"+bookService);
        System.out.println("----------------------------------------------");
        bookService.say();
    }
}
