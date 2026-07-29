package org.bluebridge.service;

import org.bluebridge.dao.UserDao;
import org.bluebridge.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 *
 * @author lingwh
 * @date 2019/11/19 13:46
 */
@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserDao userDao;

    /**
     * SpringBoot 缓存原理
     *
     * CacheManager 创建缓存组件(如 Redis 缓存组件)，由缓存组件来对缓存执行实际的 CRUD 操作
     * 加入 Redis 启动器后，容器中保存的是 RedisManager
     * RedisManager 帮我们创建 RedisCache 作为缓存组件， RedisCache 通过操作 Redis 来缓存数据
     */
    @Override
    @Cacheable(cacheNames = {"user"},key = "#id")
    public User getUserById(String id) {
        return userDao.getUserById(id);
    }
}
