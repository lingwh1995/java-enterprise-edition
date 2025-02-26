package org.bluebridge.service;

import org.bluebridge.dao.UserDao;
import org.bluebridge.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author ronin
 */
@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserDao userDao;

    /**
     * SpringBoot缓存原理:
     *      CacheManager创建缓存组件(如Redis缓存组件),由缓存组件来对缓存执行实际的CRUD操作
     *      加入Redis启动器后,容器中保存的是RedisManager
     *      RedisManager帮我们创建RedisCache作为缓存组件,RedisCache通过操作Redis来缓存数据
     */
    @Override
    @Cacheable(cacheNames = {"user"},key = "#id")
    public User getUserById(String id) {
        return userDao.getUserById(id);
    }
}
