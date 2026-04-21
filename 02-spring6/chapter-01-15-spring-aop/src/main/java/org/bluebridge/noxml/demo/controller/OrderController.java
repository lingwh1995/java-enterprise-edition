package org.bluebridge.noxml.demo.controller;

import org.bluebridge.noxml.demo.domain.Order;
import org.bluebridge.noxml.demo.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {

    @Autowired
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
