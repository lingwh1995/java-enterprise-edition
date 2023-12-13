package org.bluebridge.annotation.genericpointcut.service;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service("orderService")
public class OrderServiceImpl implements IOrderService{

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public int deleteOrderById(String id) {
        logger.info("正在执行删除操作...");
        //返回数据库中被影响的条数
        return 1;
    }
}
