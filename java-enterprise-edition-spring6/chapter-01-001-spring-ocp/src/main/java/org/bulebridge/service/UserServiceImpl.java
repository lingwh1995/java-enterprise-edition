package org.bulebridge.service;

import org.bulebridge.dao.IUserDao;
import org.bulebridge.dao.UserDaoForMysql;
import org.bulebridge.dao.UserDaoForOracle;
import org.bulebridge.domain.User;

public class UserServiceImpl implements IUserService{
    /**
     * 1.0版本应用采用Mysql数据库进行开发
     * 带来的问题?
     *      当2.0版本应用需要将数据库修改为Oracle数据库的时候,要修改现有代码，然后再进行大规模单元测试
     */
    //private IUserDao userDao = new UserDaoForMysql();

    /**
     * 模拟2.0版本应用将数据库由Mysql切换为Oracle
     */
    private IUserDao userDao = new UserDaoForOracle();

    @Override
    public User findUserByUserId(String userId) {
        return userDao.findUserByUserId(userId);
    }
}
