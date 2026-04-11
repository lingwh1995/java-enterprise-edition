package org.bluebridge.anno.test;

import org.bluebridge.anno.dao.PersonDao;
import org.bluebridge.anno.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author lingwh
 * @desc
 * @date   2019/3/21 14:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-aop-anno.xml")
public class AoPAnnoTest {

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
