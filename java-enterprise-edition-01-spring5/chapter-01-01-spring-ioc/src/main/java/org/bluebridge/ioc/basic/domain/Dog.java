package org.bluebridge.ioc.basic.domain;

public class Dog {
    private String name;
    private double price;

    /**
     * Setter注入时用到
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter注入时用到
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
