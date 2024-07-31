package com.dragonsoft.configuration.service;

import com.dragonsoft.configuration.dao.UserDao;
import com.dragonsoft.configuration.domain.User;

public class UserServiceImpl implements IUserService{

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
