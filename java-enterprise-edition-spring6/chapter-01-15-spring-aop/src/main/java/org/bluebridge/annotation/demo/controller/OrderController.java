package org.bluebridge.annotation.demo.controller;

import org.bluebridge.annotation.demo.domain.Order;
import org.bluebridge.annotation.demo.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {

    @Autowired
    private IOrderService orderService;

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
