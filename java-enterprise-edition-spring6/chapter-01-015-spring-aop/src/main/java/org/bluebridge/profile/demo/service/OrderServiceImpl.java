package org.bluebridge.profile.demo.service;

import org.bluebridge.profile.demo.dao.OrderDao;
import org.bluebridge.profile.demo.domain.Order;

public class OrderServiceImpl implements IOrderService {

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
