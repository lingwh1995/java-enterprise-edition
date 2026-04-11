package org.bluebridge.annotation.autowired.bytype.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    private static final Logger logger = LogManager.getLogger(UserDao.class);

    public void deleteById(String id) {
        logger.info("正在执行根据id删除...[使用注解完成DI-使用autowired按类型注入]");
    }
}
