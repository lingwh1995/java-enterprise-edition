package org.bluebridge.annotation.autowired.byname.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository("orderDaoForOracle")
public class OrderDaoForOracle implements IOrderDao {

    private static final Logger logger = LogManager.getLogger(OrderDaoForOracle.class);

    @Override
    public void deleteById(String id) {
        logger.info("Oracle正在执行根据id删除...[使用注解完成DI-使用autowired按名称注入]");
    }
}
