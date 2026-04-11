package org.bluebridge.profile.demo.user.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDao {
    private static final Logger logger = LogManager.getLogger(UserDao.class);

    public void deleteUserById(String id) {
        logger.info("正在执行根据id删除操作...[使用XML配置完成DI]");
    }
}
