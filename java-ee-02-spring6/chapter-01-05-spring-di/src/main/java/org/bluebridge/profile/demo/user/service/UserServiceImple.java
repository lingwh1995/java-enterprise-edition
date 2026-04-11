package org.bluebridge.profile.demo.user.service;

import org.bluebridge.profile.demo.user.dao.UserDao;

/**
 * 使用setter方式为属性注入引用类型的值
 */
public class UserServiceImple implements IUserService{

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void deleteUserById(String id) {
        userDao.deleteUserById(id);
    }
}
