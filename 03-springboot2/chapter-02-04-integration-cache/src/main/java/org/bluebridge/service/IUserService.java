package org.bluebridge.service;

import org.bluebridge.domain.User;

/**
 * 用户服务接口
 *
 * @author lingwh
 * @date 2019/11/18 11:20
 */
public interface IUserService {

    /**
     * 初步测试 @Cacheable
     * 根据 id 获取 User 对象
     * @param id
     * @return
     */
    User getUserByIdWithCacheable(String id);

    /**
     * 测试自定义生成缓存数据的 key
     * 根据 id 获取 User 对象
     * @param id
     * @return
     */
    User getUserByIdWithCustomerKeyGenerator(String id);

    /**
     * 根据 id 获取 User 对象
     * @param id
     * @return
     */
    User getUserById(String id);

    /**
     * 测试 @Caching() 配置复杂缓存策略 + @CachePut 高级用法：根据 id 获取 User 对象部分
     * 根据 id 获取 User 对象
     * @param id
     * @return
     */
    User getUserByIdWithCaching(String id);

    /**
     * 测试 @Caching() 配置复杂缓存策略 + @CachePut 高级用法：根据 username 获取 User 对象部分
     * @param username
     * @return
     */
    User getUserByUsernameWithCaching(String username);

    /**
     * 更新 User 对象
     * @param user
     * @return
     */
    User updateUser(User user);

    /**
     * 根据 id 删除 User 对象
     * @param id
     * @return
     */
    int deleteUser(String id);

    /**
     * 测试 CacheManger 的 API
     * @return
     */
    String cacheManager();
}
