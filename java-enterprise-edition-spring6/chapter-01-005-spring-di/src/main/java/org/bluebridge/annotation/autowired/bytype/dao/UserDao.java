package org.bluebridge.annotation.autowired.bytype.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    public void deleteById(String id) {
        logger.info("正在执行根据id删除...[使用注解完成DI-使用autowired按类型注入]");
    }
}
