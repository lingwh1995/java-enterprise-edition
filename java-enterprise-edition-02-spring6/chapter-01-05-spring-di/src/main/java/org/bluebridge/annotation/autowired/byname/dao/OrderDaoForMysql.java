package org.bluebridge.annotation.autowired.byname.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository("orderDaoForMysql")
public class OrderDaoForMysql implements IOrderDao {

    private static final Logger logger = LogManager.getLogger(OrderDaoForMysql.class);

    @Override
    public void deleteById(String id) {
        logger.info("Mysql正在执行根据id删除...[使用注解完成DI-使用autowired按名称注入]");
    }
}
