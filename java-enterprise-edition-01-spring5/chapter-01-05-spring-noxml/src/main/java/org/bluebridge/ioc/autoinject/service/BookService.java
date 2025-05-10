package org.bluebridge.ioc.autoinject.service;

import org.bluebridge.ioc.autoinject.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author ronin
 */
@Service
public class BookService {

    @Qualifier(value="bookDao2")
    @Autowired
    private BookDao bookDao;

    private String label = "被@Service标注的Service";

    public BookDao getBookDao() {
        return bookDao;
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void say(){
        System.out.println("----------------------------------------------");
        System.out.println("BookService中注入的BookDao是:"+bookDao);
        System.out.println("----------------------------------------------");
        bookDao.say();
    }

    @Override
    public String toString() {
        return "BookService{" +
                "label='" + label + '\'' +
                '}';
    }
}
