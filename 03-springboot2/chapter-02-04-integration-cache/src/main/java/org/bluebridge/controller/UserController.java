package org.bluebridge.controller;

import org.bluebridge.domain.User;
import org.bluebridge.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户控制器
 *
 * @author lingwh
 * @date 2019/11/18 14:00
 */
@Controller
public class UserController {

    @Autowired
    private IUserService UserService;

    /**
     * 初步测试 @Cacheable 注解，通过控制台打印的 sql 语句数量来查看 @Cacheable 是否生效
     * id = 002 打印两条语句
     * id = 003 或其他之  打印一条语句
     *
     * 注意：
     * 1. 此方法通过 SpringBootTest 测试
     * 2. @Cacheable 中设置了 unless="#id=='002'"，表示当 #id=='002 不缓存
     *
     * @param id
     * @return
     */
    public User getUserByIdWithCacheable(String id) {
        return UserService.getUserByIdWithCacheable(id);
    }

    /**
     * 测试自定义生成缓存数据的 key
     *
     * http://localhost:8080/keygenerator/001
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/keygenerator/{id}")
    public User getUserByIdWithCustomerKeyGenerator(@PathVariable("id") String id) {
        return UserService.getUserByIdWithCustomerKeyGenerator(id);
    }

    /**
     * http://localhost:8080/select/001
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/select/{id}")
    public User getUserById(@PathVariable("id") String id) {
        return UserService.getUserById(id);
    }

    /**
     * 测试 @CachePut：同步更新缓存的功能(更新数据库中的数据，并且根据 id 更新缓存中的数据)
     *
     * 测试步骤
     * 1. 先根据 id=001 查询 http://localhost:8080/select/001
     * 2. 再根据 id=001 更新数据库中数据 http://localhost:8080/update/001
     * 3. 此时再访问 http://localhost:8080/select/001，系统不会发送 sql 了，而是根据直接从缓存中获取 key=001 的缓存数据
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/update/{id}")
    public User updateUser(@PathVariable("id") String id) {
        User user = new User();
        user.setId(id);
        user.setUsername("zhangsan");
        user.setPassword("1234568");
        UserService.updateUser(user);
        return user;
    }

    /**
     * 测试删除数据库中记录时，同时清空缓存中数据，否则前台发请求还是能查询到已经删除了的数据
     *
     * 先查询：    http://localhost:8080/select/001
     * 再删除：    http://localhost:8080/delete/001
     * 再查询：    http://localhost:8080/select/001
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        UserService.deleteUser(id);
        return "{\"MESSAGE\":\"删除User\"}";
    }

    /**
     * 根据 id 查询之后，在进行缓存的同时会以 id 作为 key 把 User 对象在缓存中保存一份，也会以 username 作为 key 把 user 对象在缓存中保存一份
     *
     * 先访问： http://localhost:8080/caching_id/001   打印 sql
     * 再访问： http://localhost:8080/caching_username/zhangsan  直接从缓存中获取，不会打印 sql
     *
     * 测试 @Caching() 配置复杂缓存策略 + @CachePut 高级用法：根据 id 获取 User 对象部分
     *
     * 注意
     * 1. 需要数据库中有一条 sql：INSERT INTO T_USER VALUES ('001','zhangsan','28');
     * 2. 本方法在 SpringBootTest 环境下进行测试
     */
    @ResponseBody
    @RequestMapping("/caching_id/{id}")
    public User getUserByIdWithCaching(@PathVariable("id") String id) {
        return UserService.getUserByIdWithCaching(id);
    }

    /**
     * 根据 id 查询之后，在进行缓存的同时会以 id 作为 key 把 User 对象在缓存中保存一份，也会以 username 作为 key 把 user 对象在缓存中保存一份
     *
     * 测试方法：
     * 先访问： http://localhost:8080/caching_id/001   打印 sql
     * 再访问： http://localhost:8080/caching_username/zhangsan  直接从缓存中获取，不会打印 sql
     *
     * 测试 @Caching() 配置复杂缓存策略 + @CachePut 高级用法：根据 username 获取 User 对象部分
     *
     * 注意
     * 1. 需要数据库中有一条 sql：INSERT INTO T_USER VALUES ('001','zhangsan','28');
     * 2. 本方法在 SpringBootTest 环境下进行测试
     */
    @ResponseBody
    @RequestMapping("/caching_username/{username}")
    public User getUserByUsernameWithCaching(@PathVariable("username") String username) {
        return UserService.getUserByUsernameWithCaching(username);
    }

    /**
     * 测试 CacheManger 的 API
     *
     * 测试步骤
     * 1. 给缓存中放入数据： http://localhost:8080/cachemanager
     * 2. 使用 cachemanager 从缓存中获取数据： http://localhost:8080/cachemanager
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/cachemanager")
    public String cacheManager() {
        return UserService.cacheManager();
    }
}
