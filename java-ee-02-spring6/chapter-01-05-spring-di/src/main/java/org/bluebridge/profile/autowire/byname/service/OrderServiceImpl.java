package org.bluebridge.profile.autowire.byname.service;

import org.bluebridge.profile.autowire.byname.dao.OrderDao;

/**
 * 根据属性名称autowire   需要为属性创建setter方法
 */
public class OrderServiceImpl implements IOrderService {

    private OrderDao orderDao;

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public void deleteById(String id) {
        orderDao.deleteById(id);
    }
}
