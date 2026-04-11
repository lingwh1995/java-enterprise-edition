package org.bluebridge.annotation.genericpointcut.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("orderService")
public class OrderServiceImpl implements IOrderService{

    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    @Override
    public Integer deleteOrderById(String id) {
        logger.info("正在执行删除操作...");
        //返回数据库中被影响的条数
        return 1;
    }
}
