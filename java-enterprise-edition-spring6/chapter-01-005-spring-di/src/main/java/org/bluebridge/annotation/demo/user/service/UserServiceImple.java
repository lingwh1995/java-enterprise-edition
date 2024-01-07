package org.bluebridge.annotation.demo.user.service;

import org.bluebridge.annotation.demo.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 使用setter方式为属性注入引用类型的值
 */
@Service
public class UserServiceImple implements IUserService {

    @Autowired
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void deleteUserById(String id) {
        userDao.deleteUserById(id);
    }
}
