package org.bluebridge.annotation.autowired.bytype.service;

import org.bluebridge.annotation.autowired.bytype.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserDao userDao;

    @Override
    public void deleteById(String id) {
        userDao.deleteById(id);
    }
}
