package org.bluebridge.service;

import org.bluebridge.dao.IUserDao;
import org.bluebridge.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 *
 * @author lingwh
 * @date 2024/9/2 11:25
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public User findById(String id) {
        return userDao.findById(id).get();
    }
}
