package org.bluebridge.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.bluebridge.domain.User;

/**
 * 用户数据访问接口
 *
 * @author lingwh
 * @date 2019/11/18 14:56
 */
public interface IUserDao {

    /**
     * 初步测试 @Cacheable
     * 根据 id 获取 User 对象
     * @param id
     * @return
     */
    @Select("SELECT ID,USERNAME,PASSWORD FROM T_USER WHERE ID = #{id}")
    User getUserByIdWithCacheable(String id);

    /**
     * 测试自定义生成缓存数据的 key
     * 根据 id 获取 User 对象
     * @param id
     * @return
     */
    @Select("SELECT ID,USERNAME,PASSWORD FROM T_USER WHERE ID = #{id}")
    User getUserByIdWithCustomerKeyGenerator(String id);

    /**
     * 根据 id 获取 User 对象
     * @param id
     * @return
     */
    @Select("SELECT ID,USERNAME,PASSWORD FROM T_USER WHERE ID = #{id}")
    User getUserById(String id);

    /**
     * 测试 @Caching() 配置复杂缓存策略 + @CachePut 高级用法：根据 id 获取 User 对象部分
     * 根据 id 获取 User 对象
     * @param id
     * @return
     */
    @Select("SELECT ID,USERNAME,PASSWORD FROM T_USER WHERE ID = #{id}")
    User getUserByIdWithCaching(String id);

    /**
     * 测试 @Caching() 配置复杂缓存策略 + @CachePut 高级用法：根据 username 获取 User 对象部分
     * 根据 id 获取 User 对象
     * @param username
     * @return
     */
    @Select("SELECT ID,USERNAME,PASSWORD FROM T_USER WHERE USERNAME = #{username}")
    User getUserByUsernameWithCaching(String username);

    /**
     * 更新 User 对象
     * @param user
     * @return
     */
    @Update("UPDATE T_USER SET USERNAME = #{username},PASSWORD = #{password} WHERE ID = #{id}")
    int updateUser(User user);

    /**
     * 根据 id 删除 User 对象
     * @param id
     * @return
     */
    @Delete("DELETE FROM T_USER WHERE ID = #{id}")
    int deleteUser(String id);
}
