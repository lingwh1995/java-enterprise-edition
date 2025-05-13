package org.bluebridge.profile.base.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bluebridge.profile.base.domain.User;

public class UserDao {

    private static final Logger logger = LogManager.getLogger(UserDao.class);

    public void addUser(User User) {
        logger.info("正在执行新增用户操作...");
    }

    public int deleteUserById(String id) {
        logger.info("正在执行删除用户操作...");
        //返回数据库中被影响的条数
        return 1;
    }

    public void updateUser(User user) {
        logger.info("正在执行更新用户操作...");
    }

    public User getUserById(String id) {
        logger.info("正在执行查询用户操作...");
        //模拟从数据库中查询到了一个User对象
        User user = new User("001", "张三", 20);
        return user;
    }

    public void login() {
        logger.info("正在执行登录操作...");
    }
}
