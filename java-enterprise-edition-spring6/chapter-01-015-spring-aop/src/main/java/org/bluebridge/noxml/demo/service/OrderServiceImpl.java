package org.bluebridge.noxml.demo.service;

import org.bluebridge.noxml.demo.dao.OrderDao;
import org.bluebridge.noxml.demo.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderDao orderDao;

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public void addOrder(Order order) {
        orderDao.addOrder(order);
    }

    @Override
    public void deleteOrderById(String id) {
        orderDao.deleteOrderById(id);
    }

    @Override
    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    @Override
    public Order getOrderById(String id) {
        return orderDao.getOrderById(id);
    }
}
