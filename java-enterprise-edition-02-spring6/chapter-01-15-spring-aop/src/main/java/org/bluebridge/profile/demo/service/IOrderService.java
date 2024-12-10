package org.bluebridge.profile.demo.service;

import org.bluebridge.profile.demo.domain.Order;

public interface IOrderService {
    void addOrder(Order order);
    void deleteOrderById(String id);
    void updateOrder(Order order);
    Order getOrderById(String id);
}
