package org.bluebridge.ioc.beanlifecycle.domain;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/10 11:17
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
