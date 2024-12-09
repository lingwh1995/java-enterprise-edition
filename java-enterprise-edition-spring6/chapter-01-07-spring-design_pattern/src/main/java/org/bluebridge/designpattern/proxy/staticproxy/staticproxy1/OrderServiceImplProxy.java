package org.bluebridge.designpattern.proxy.staticproxy.staticproxy1;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class OrderServiceImplProxy implements IOrderService{

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImplProxy.class);

    private IOrderService target;

    public OrderServiceImplProxy(IOrderService target) {
        this.target = target;
    }

    @Override
    public void addOrder() {
        long start = System.currentTimeMillis();
        target.addOrder();
        long end = System.currentTimeMillis();
        logger.info("OrderServiceImpl.addOrder()执行花费了: " + (end-start) + "ms");
    }

    @Override
    public void deleteOrderById() {
        long start = System.currentTimeMillis();
        target.deleteOrderById();
        long end = System.currentTimeMillis();
        logger.info("OrderServiceImpl.deleteOrderById()执行花费了: " + (end-start) + "ms");
    }

    @Override
    public void updateOrder() {
        long start = System.currentTimeMillis();
        target.updateOrder();
        long end = System.currentTimeMillis();
        logger.info("OrderServiceImpl.updateOrder()执行花费了: " + (end-start) + "ms");
    }

    @Override
    public void getOrderById() {
        long start = System.currentTimeMillis();
        target.getOrderById();
        long end = System.currentTimeMillis();
        logger.info("OrderServiceImpl.getOrderById()执行花费了: " + (end-start) + "ms");
    }
}
