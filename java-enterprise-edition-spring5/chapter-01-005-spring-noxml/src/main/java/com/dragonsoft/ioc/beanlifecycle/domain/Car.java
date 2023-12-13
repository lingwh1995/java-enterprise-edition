package com.dragonsoft.ioc.beanlifecycle.domain;

/**
 * @author ronin
 */
public class Car {

    public Car() {
        System.out.println("Car constructor......");
    }

    public void init(){
        System.out.println("Car init......");
    }

    public void destory(){
        System.out.println("Car destory......");
    }
}
