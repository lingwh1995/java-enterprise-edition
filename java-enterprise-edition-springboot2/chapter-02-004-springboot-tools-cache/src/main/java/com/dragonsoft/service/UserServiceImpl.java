package com.dragonsoft.service;

import com.dragonsoft.dao.IUserDao;
import com.dragonsoft.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.Collection;


/**
 * @author ronin
 */
@Service
@CacheConfig(cacheNames={"user"})
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserDao userDao;

    @Autowired
    private CacheManager cacheManager;

    /**
     * 测试@Cacheable注解:
     *      将方法的运行结果进行缓存,以后再查询相同的数据,直接从缓存中获取,调用时机:查询时先看缓存
     *          中有没有,有的话直接去缓存中获取,缓存中不存在的话再去数据库中查询获取
     *      注解属性:
     *          cacheNames:
     *              数组形式,可以指定多个名称
     *          key：默认是方法参数的值,
     *          生成key的方式:
     *              1.spel表达式获取
     *                  #root.methodName:获取方法名
     *                  #id:获取方法参数id的值
     *              2.keyGenerator:key的生成器
     *          condition:指定什么条件下进行缓存
     *              condition="#id!='002'"      id不等于002的时候缓存
     *          unless:条件为true不缓存,表示否定
     *              unless="#id=='002'"
     *          sync: 是否异步
     *
     * CacheManager:
     *      管理多个Cache组件,对缓存真正的CRUD操作在Cache组件中的,每一个缓存组件有自己 唯一的名字
     * @param id
     * @return
     */
    @Override
    @Cacheable(/*cacheNames = {"user"},*/key ="#root.methodName+'['+#id+']'",unless="#id=='002'")
    public User getUserByIdWithCacheable(String id) {
        return userDao.getUserByIdWithCacheable(id);
    }

    /**
     * 测试自定义生成缓存数据的key
     * @param id
     * @return
     */
    @Override
    @Cacheable(/*cacheNames = {"user"},*/keyGenerator = "myKeyGenerator")
    public User getUserByIdWithCustomerKeyGenerator(String id) {
        return userDao.getUserByIdWithCustomerKeyGenerator(id);
    }

    /**
     * 根据id获取User对象
     * @param id
     * @return
     */
    @Override
    @Cacheable(/*cacheNames = {"user"},*/key="#id")
    public User getUserById(String id) {
        return userDao.getUserById(id);
    }

    /**
     * 测试@CachePut注解:同步更新缓存的功能(更新数据库中的数据,并且根据id更新缓存中的数据)
     *      属性用法基本同@Cacheable
     *      注解属性:
     *      cacheNames:
     *              数组形式,可以指定多个名称
     *      key的写法:
     *          key="#id"
     *          key="#result.id"    注意:这个表达式只能在@CachePut中使用,不能在@Cacheable中使用
     *          key="#user.id"
     * 注意:
     *      此处遇到一个bug
     *          java.lang.ClassCastException: java.lang.Integer cannot be cast to com.dragonsoft.domain.User
     *      解决方式1:
     *          Service层写法:返回值为User类型数据
     *              public User updateUser(User user) {}
     *          Dao层写法:返回值为int类型数据
     *              int updateUser(User user);
     *          总之不能直接把dao层的返回值直接返回到Controlelr层
     *      解决方式2:
     *          Service层写法:返回值为int类型数据
     *              查询方法:@Cacheable中不要配置key属性相关的内容
     *              @Cacheable(cacheNames = {"user"})
     *              public User getUserById(String id) {}
     *              更新方法:@CachePut中不要配置key属性相关的内容
     *              @CachePut(cacheNames={"user"},key="#user.id")
     *              public int updateUser(User user) {}
     *          Dao层写法:返回值为int类型数据
     *              int updateUser(User user);
     * @param user
     * @return
     */
    @Override
    @CachePut(/*cacheNames = {"user"},*/key="#user.id")
    public User updateUser(User user) {
        userDao.updateUser(user);
        return user;
    }

    /**
     *  CacheEvict注解属性配置:
     *      allEntries = true,为true会删除 cacheNames="user"的缓存中 中所有的缓存
     *      beforeInvocation = false,在方法执行之后清空缓存,方法发生了异常缓存就不会被清除
     *      beforeInvocation = true,在方法执行之前清空缓存,方法发生了异常缓存也会被清除
     * @param id
     * @return
     */
    @Override
    @CacheEvict(/*cacheNames = {"user"},*/allEntries = false,beforeInvocation = false)
    public int deleteUser(String id) {
        return userDao.deleteUser(id);
    }

    /**
     * 测试@Caching()配置复杂缓存策略+@CachePut高级用法:根据id获取User对象部分
     * 根据id获取User对象,并以id作为key将User对象放入缓存中
     *      同时以username作为key将User对象放入缓存中
     *      同时以password作为key将User对象放入缓存中
     * 效果:根据id查询后,根据id/username/password查询都会从缓存中获取
     *
     * 关于@CachePut注解:
     *      被@CachePut注解标注的方法一定会执行
     * 关于@Cacheable注解:
     *      被@Cacheable注解标注的方法会先从缓存中查询数据,当缓存中没有想要的数据时才从数据库中查询
     * @param id
     * @return
     */
    @Override
    @Caching(
            cacheable = {@Cacheable(/*cacheNames = {"user"},*/key="#id")},
            put = {@CachePut(/*cacheNames = {"user"},*/key="#result.username")}
    )
    public User getUserByIdWithCaching(String id) {
        return userDao.getUserByIdWithCaching(id);
    }

    /**
     * 测试@Caching()配置复杂缓存策略+@CachePut高级用法:根据username获取User对象部分
     * @param username
     * @return
     */
    @Override
    @Cacheable(/*cacheNames = "user",*/key = "#username")
    public User getUserByUsernameWithCaching(String username) {
        return userDao.getUserByUsernameWithCaching(username);
    }

    /**
     * 测试CacheManger的API:
     *      1.获取所有缓存的名称
     *      2.根据缓存名称获取具体的缓存对象
     * @return
     */
    @Override
    public String cacheManager() {
        System.out.println(cacheManager.getClass());
        //获取所有缓存的名称
        Collection<String> cacheNames = cacheManager.getCacheNames();
        System.out.println(cacheNames.toString());
        //根据缓存名称获取具体的缓存对象
        Cache user = cacheManager.getCache("user");
        return "{\"MESSAGE\":\"我是用来测试CacheManager的API的\"}";
    }
}
