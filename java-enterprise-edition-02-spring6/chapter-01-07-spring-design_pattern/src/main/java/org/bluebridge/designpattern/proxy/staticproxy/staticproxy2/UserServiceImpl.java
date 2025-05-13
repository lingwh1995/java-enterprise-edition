package org.bluebridge.designpattern.proxy.staticproxy.staticproxy2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements IUserService{

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public void addUser(User user) {
        logger.info("正在执行新增用户操作...");
    }

    @Override
    public void deleteUserById(String id) {
        logger.info("正在执行删除用户操作...");
    }

    @Override
    public void updateUser(User user) {
        logger.info("正在执行修改用户操作...");
    }

    @Override
    public User getUserById(String id) {
        logger.info("正在执行查询用户操作...");
        //模拟从数据中根据id查询到了一个用户
        User user = new User("001","张三",25);
        return user;
    }
}
