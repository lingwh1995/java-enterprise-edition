package org.bulebridge.ioc.beanlifecycle.domain;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author ronin
 */
public class Dog {

    public Dog() {
        System.out.println("Dog Constructor......");
    }

    @PostConstruct
    public void init(){
        System.out.println("Dog init......");
    }

    @PreDestroy
    public void destory(){
        System.out.println("Dog destory......");
    }
}
