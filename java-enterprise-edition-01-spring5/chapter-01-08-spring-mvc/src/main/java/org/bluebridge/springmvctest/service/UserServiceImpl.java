package org.bluebridge.springmvctest.service;

import org.bluebridge.springmvctest.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ronin
 * @version V1.0
 * @desc
 * @since 2019/7/22 14:28
 */
@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private UserDao userDao;

    @Override
    public void eat() {
        userDao.eat();
    }
}
