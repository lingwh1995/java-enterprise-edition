package org.bluebridge.juint4.service;

import org.bluebridge.juint4.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserDao userDao;

    @Override
    public void deleteUserById(String id) {
        userDao.deleteUserById(id);
    }
}
