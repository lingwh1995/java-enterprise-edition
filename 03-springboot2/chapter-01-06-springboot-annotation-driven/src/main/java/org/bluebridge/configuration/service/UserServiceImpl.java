package org.bluebridge.configuration.service;

import org.bluebridge.configuration.dao.UserDao;
import org.bluebridge.configuration.domain.User;

/**
 * 用户服务实现类
 *
 * @author lingwh
 * @date 2019/11/19 09:48
 */
public class UserServiceImpl implements IUserService {

    private UserDao userDao;

    /**
     * 使用 set 方式注入
     *
     * @param userDao 用户 DAO 实例
     */
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }
}
