package org.bluebridge.service;

import org.bluebridge.dao.UserDao;

public class UserService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void deleteUserById(String id) {
        userDao.deleteUserById(id);
    }
}
