package org.bulebridge.dao;

import org.bulebridge.domain.User;

public class UserDaoForMysql implements IUserDao{

    @Override
    public User findUserByUserId(String userId) {
        System.out.println("Mysql执行了根据userId查询功能...");
        //模拟从数据库中查询到一个User对象
        User user = new User("001","Mysql");
        return user;
    }
}
