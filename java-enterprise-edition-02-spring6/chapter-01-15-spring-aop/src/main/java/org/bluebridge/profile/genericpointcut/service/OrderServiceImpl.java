package org.bluebridge.profile.genericpointcut.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderServiceImpl implements IOrderService {

    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    @Override
    public void deleteOrderById(String id) {
        logger.info("正在执行删除操作...");
    }
}
