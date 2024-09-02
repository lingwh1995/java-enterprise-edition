package com.dragonsoft.service;

import com.dragonsoft.domain.User;

/**
 * @author ronin
 */
public interface IUserService {
    /**
     * 初步测试@Cacheable
     * 根据id获取User对象
     * @param id
     * @return
     */
    User getUserByIdWithCacheable(String id);

    /**
     * 测试自定义生成缓存数据的key
     * 根据id获取User对象
     * @param id
     * @return
     */
    User getUserByIdWithCustomerKeyGenerator(String id);

    /**
     * 根据id获取User对象
     * @param id
     * @return
     */
    User getUserById(String id);

    /**
     * 测试@Caching()配置复杂缓存策略+@CachePut高级用法:根据id获取User对象部分
     * 根据id获取User对象
     * @param id
     * @return
     */
    User getUserByIdWithCaching(String id);
    /**
     * 测试@Caching()配置复杂缓存策略+@CachePut高级用法:根据username获取User对象部分
     * @param username
     * @return
     */
    User getUserByUsernameWithCaching(String username);

    /**
     * 更新User对象
     * @param user
     * @return
     */
    User updateUser(User user);

    /**
     * 根据id删除User对象
     * @param id
     * @return
     */
    int deleteUser(String id);

    /**
     * 测试CacheManger的API
     * @return
     */
    String cacheManager();
}
