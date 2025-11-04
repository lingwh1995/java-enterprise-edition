package org.bluebridge.configuration.service;

import org.bluebridge.configuration.dao.UserDao;
import org.bluebridge.configuration.domain.User;

public class UserServiceImpl implements IUserService {

    private UserDao userDao;

    /**
     * 使用set方式注入
     * @param userDao
     */
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }
}
