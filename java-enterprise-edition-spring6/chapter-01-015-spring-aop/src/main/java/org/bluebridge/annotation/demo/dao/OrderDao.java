package org.bluebridge.annotation.demo.dao;

import org.bluebridge.annotation.demo.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {

    private static final Logger logger = LoggerFactory.getLogger(OrderDao.class);

    public void addOrder(Order order) {
        logger.info("正在执行新增订单操作...");
    }

    public void deleteOrderById(String id) {
        logger.info("正在执行删除订单操作...");
    }

    public void updateOrder(Order order) {
        logger.info("正在执行更新订单操作...");
    }

    public Order getOrderById(String id) {
        logger.info("正在执行查询订单操作...");
        //模拟从数据库查询到了一条记录
        Order order = new Order("001", "25.8");
        return order;
    }
}
