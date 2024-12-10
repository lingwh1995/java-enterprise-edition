package org.bluebridge.profile.base.service;


import org.bluebridge.profile.base.dao.UserDao;
import org.bluebridge.profile.base.domain.User;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class UserServiceImpl implements IUserService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public int deleteUserById(String id) {
       return userDao.deleteUserById(id);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public User getUserById(String id) {
        return userDao.getUserById(id);
    }

    @Override
    public void login() {
        int i = 1 / 0;
        userDao.login();
    }

}
