package org.bluebridge.profile.demo.controller;

import org.bluebridge.profile.demo.domain.Order;
import org.bluebridge.profile.demo.service.IOrderService;

public class OrderController {

    private IOrderService orderService;

    public void setOrderService(IOrderService orderService) {
        this.orderService = orderService;
    }

    public void addOrder(Order order) {
        orderService.addOrder(order);
    }

    public void deleteOrderById(String id) {
        orderService.deleteOrderById(id);
    }

    public void updateOrder(Order order) {
        orderService.updateOrder(order);
    }

    public Order getOrderById(String id) {
        return orderService.getOrderById(id);
    }
}
