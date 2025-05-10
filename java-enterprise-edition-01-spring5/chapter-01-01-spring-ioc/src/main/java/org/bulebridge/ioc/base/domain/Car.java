package org.bulebridge.ioc.base.domain;

/**
 * 构造方法注入
 */
public class Car {
    private String name;
    private double money;

    /**
     * 构造方法注入时用到
     * @param name
     * @param money
     */
    public Car(String name, double money) {
        this.name = name;
        this.money = money;
    }

    @Override
    public String toString() {
        return "Car [name=" + name + ", money=" + money + "]";
    }
}
