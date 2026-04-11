package org.bluebridge.designpattern.proxy.staticproxy.staticproxy1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderServiceImpl implements IOrderService{

    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    @Override
    public void addOrder() {
        try {
            Thread.sleep(1000);
            logger.info("新增订单...");
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrderById() {
        try {
            Thread.sleep(2000);
            logger.info("删除订单...");
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateOrder() {
        try {
            Thread.sleep(3000);
            logger.info("更新订单...");
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getOrderById() {
        try {
            Thread.sleep(4000);
            logger.info("查询订单...");
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
