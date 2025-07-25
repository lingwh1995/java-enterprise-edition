package org.bluebridge.ioc.autowiredlocation.service;

import org.bluebridge.ioc.autowiredlocation.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/11 10:35
 */
@Service
public class UserService {

    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    /**
     * 测试Auto标注在Setter方法上
     * @param userDao
     */
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void say() {
        userDao.say();
    }
}
