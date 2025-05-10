package org.bulebridge.ioc.base.domain;

public class Dog {
    private String name;
    private double money;

    /**
     * Setter注入时用到
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter注入时用到
     * @param money
     */
    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Car [name=" + name + ", money=" + money + "]";
    }
}
