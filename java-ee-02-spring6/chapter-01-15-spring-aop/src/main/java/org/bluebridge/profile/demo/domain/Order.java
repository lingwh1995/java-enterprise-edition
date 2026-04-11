package org.bluebridge.profile.demo.domain;

public class Order {

    private String id;
    private String OrderPrice;

    public Order() {
    }

    public Order(String id, String orderPrice) {
        this.id = id;
        OrderPrice = orderPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderPrice() {
        return OrderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        OrderPrice = orderPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", OrderPrice='" + OrderPrice + '\'' +
                '}';
    }
}
