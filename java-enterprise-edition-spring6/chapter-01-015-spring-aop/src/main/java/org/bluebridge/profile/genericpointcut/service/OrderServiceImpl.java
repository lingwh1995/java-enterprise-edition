package org.bluebridge.profile.genericpointcut.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderServiceImpl implements IOrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public void deleteOrderById(String id) {
        logger.info("正在执行删除操作...");
    }
}
