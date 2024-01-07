package org.bluebridge.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    public void deleteUserById(String id) {
        logger.info("正在执行根据id删除操作...");
    }
}
