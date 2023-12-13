package org.bluebridge.noxml.demo.service;

import org.bluebridge.noxml.demo.domain.Order;

public interface IOrderService {
    void addOrder(Order order);
    void deleteOrderById(String id);
    void updateOrder(Order order);
    Order getOrderById(String id);
}
