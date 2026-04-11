package org.bluebridge.ioc.basic.domain;

/**
 * @author lingwh
 * @desc   演示构造方法注入
 * @date   2019/3/17 9:38
 */
public class Car {
    private String name;
    private double price;

    /**
     * 构造方法注入时用到
     * @param name
     * @param price
     */
    public Car(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
