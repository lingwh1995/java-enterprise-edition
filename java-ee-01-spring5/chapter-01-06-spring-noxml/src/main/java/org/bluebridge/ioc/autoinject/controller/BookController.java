package org.bluebridge.ioc.autoinject.controller;

import org.bluebridge.ioc.autoinject.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/12 12:17
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
