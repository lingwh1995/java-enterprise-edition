package com.dragonsoft.dao;

import com.dragonsoft.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


/**
 * @author ronin
 * @version V1.0
 * @since 2019/11/18 14:56
 */
public interface IUserDao {
    /**
     * 初步测试@Cacheable
     * 根据id获取User对象
     * @param id
     * @return
     */
    @Select("SELECT ID,USERNAME,PASSWORD FROM T_USER WHERE ID = #{id}")
    User getUserByIdWithCacheable(String id);

    /**
     * 测试自定义生成缓存数据的key
     * 根据id获取User对象
     * @param id
     * @return
     */
    @Select("SELECT ID,USERNAME,PASSWORD FROM T_USER WHERE ID = #{id}")
    User getUserByIdWithCustomerKeyGenerator(String id);

    /**
     * 根据id获取User对象
     * @param id
     * @return
     */
    @Select("SELECT ID,USERNAME,PASSWORD FROM T_USER WHERE ID = #{id}")
    User getUserById(String id);

    /**
     * 测试@Caching()配置复杂缓存策略+@CachePut高级用法:根据id获取User对象部分
     * 根据id获取User对象
     * @param id
     * @return
     */
    @Select("SELECT ID,USERNAME,PASSWORD FROM T_USER WHERE ID = #{id}")
    User getUserByIdWithCaching(String id);

    /**
     * 测试@Caching()配置复杂缓存策略+@CachePut高级用法:根据username获取User对象部分
     * 根据id获取User对象
     * @param username
     * @return
     */
    @Select("SELECT ID,USERNAME,PASSWORD FROM T_USER WHERE USERNAME = #{username}")
    User getUserByUsernameWithCaching(String username);

    /**
     * 更新User对象
     * @param user
     * @return
     */
    @Update("UPDATE T_USER SET USERNAME = #{username},PASSWORD = #{password} WHERE ID = #{id}")
    int updateUser(User user);

    /**
     * 根据id删除User对象
     * @param id
     * @return
     */
    @Delete("DELETE FROM T_USER WHERE ID = #{id}")
    int deleteUser(String id);
}
