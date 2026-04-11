package org.bluebridge.annotation.demo.service;

import org.bluebridge.annotation.demo.domain.Order;

public interface IOrderService {
    void addOrder(Order order);
    void deleteOrderById(String id);
    void updateOrder(Order order);
    Order getOrderById(String id);
}
