package com.dragonsoft.service;

import com.dragonsoft.dao.IUserDao;
import com.dragonsoft.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ronin
 * @version V1.0
 * @since 2019/11/19 13:46
 */
@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserDao userDao;

    @Override
    public User findById(String id) {
        return userDao.findById(id).get();
    }
}
