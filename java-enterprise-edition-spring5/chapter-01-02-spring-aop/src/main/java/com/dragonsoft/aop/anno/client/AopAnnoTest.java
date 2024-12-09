package com.dragonsoft.aop.anno.client;

import com.dragonsoft.aop.anno.dao.PersonDao;
import com.dragonsoft.aop.anno.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-aop-anno.xml")
public class AopAnnoTest {

    @Resource(name="personDao")
    private PersonDao personDao;

    @Resource(name="userService")
    private IUserService userService;

    /**
     * 为接口生成代理
     */
    @Test
    public void fun1(){
        userService.eat();
    }

    /**
     * 为类生成代理
     */
    @Test
    public void fun2(){
        personDao.save();
    }

}
