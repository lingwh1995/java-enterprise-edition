package com.dragonsoft.controller;

import com.dragonsoft.domain.User;
import com.dragonsoft.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ronin
 */
@Controller
public class UserController {

    @Autowired
    private IUserService UserService;

    /**
     * 初步测试@Cacheable注解，通过控制台打印的sql语句数量来查看@Cacheable是否生效
     *      id=002 打印两条语句
     *      id=003或其他之  打印一条语句
     * 注意:
     *      1.此方法通过SpringBootTest测试
     *      2.@Cacheable中设置了unless="#id=='002'",表示当#id=='002不缓存
     * @param id
     * @return
     */
    public User getUserByIdWithCacheable(String id) {
        return UserService.getUserByIdWithCacheable(id);
    }

    /**
     * http://localhost:8080/keygenerator/001
     * 测试自定义生成缓存数据的key
     * 测试@CachePut注解
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
     * 测试@CachePut注解
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/select/{id}")
    public User getUserById(@PathVariable("id") String id) {
        return UserService.getUserById(id);
    }

    /**
     * 测试@CachePut注解:同步更新缓存的功能(更新数据库中的数据,并且根据id更新缓存中的数据)
     * 测试步骤:
     *      1.先根据id=001查询     http://localhost:8080/select/001
     *      2.再根据id=001更新数据库中数据   http://localhost:8080/update/001
     *      3.此时再访问   http://localhost:8080/select/001,系统不会发送sql了,而是根据直接从缓存中获取key=001的缓存数据
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/update/{id}")
    public User updateUser(@PathVariable("id") String id){
        User user = new User();
        user.setId(id);
        user.setUsername("zhangsan");
        user.setPassword("1234568");
        UserService.updateUser(user);
        return user;
    }

    /**
     * 测试删除数据库中记录时,同时清空缓存中数据,否则前台发请求还是能查询到已经删除了的数据
     *      先查询:    http://localhost:8080/select/001
     *      再删除:    http://localhost:8080/delete/001
     *      再查询:    http://localhost:8080/select/001
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String id){
        UserService.deleteUser(id);
        return "{\"MESSAGE\":\"删除User\"}";
    }

    /**
     * 根据id查询之后,在进行缓存的同时会以id作为key把User对象在缓存中保存一份,也会以username作为key把user对象在缓存中保存一份
     * 测试方法:
     *      先访问:
     *          http://localhost:8080/caching_id/001   打印sql
     *      再访问:
     *          http://localhost:8080/caching_username/zhangsan  直接从缓存中获取,不会打印sql
     * 测试@Caching()配置复杂缓存策略+@CachePut高级用法:根据id获取User对象部分
     * 注意:
     *      1.需要数据库中有一条sql:INSERT INTO T_USER VALUES ('001','zhangsan','28');
     *      2.本方法在SpringBootTest环境下进行测试
     */
    @ResponseBody
    @RequestMapping("/caching_id/{id}")
    public User getUserByIdWithCaching(@PathVariable("id") String id){
        return UserService.getUserByIdWithCaching(id);
    }

    /**
     * 根据id查询之后,在进行缓存的同时会以id作为key把User对象在缓存中保存一份,也会以username作为key把user对象在缓存中保存一份
     * 测试方法:
     *      先访问:
     *          http://localhost:8080/caching_id/001   打印sql
     *      再访问:
     *          http://localhost:8080/caching_username/zhangsan  直接从缓存中获取,不会打印sql
     * 测试@Caching()配置复杂缓存策略+@CachePut高级用法:根据username获取User对象部分
     * 注意:
     *      1.需要数据库中有一条sql:INSERT INTO T_USER VALUES ('001','zhangsan','28');
     *      2.本方法在SpringBootTest环境下进行测试
     */
    @ResponseBody
    @RequestMapping("/caching_username/{username}")
    public User getUserByUsernameWithCaching(@PathVariable("username") String username){
        return UserService.getUserByUsernameWithCaching(username);
    }

    /**
     * 测试CacheManger的API
     * 测试步骤:
     *      给缓存中放入数据:
     *          http://localhost:8080/cachemanager
     *      使用cachemanager从缓存中获取数据:
     *          http://localhost:8080/cachemanager
     * @return
     */
    @ResponseBody
    @RequestMapping("/cachemanager")
    public String cacheManager(){
        return UserService.cacheManager();
    }
}
