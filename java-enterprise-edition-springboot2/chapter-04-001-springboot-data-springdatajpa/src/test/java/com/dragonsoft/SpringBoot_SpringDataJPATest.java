package com.dragonsoft;

import com.dragonsoft.dao.IUserDao;
import com.dragonsoft.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 测试SpringBoot环境下使用SpringDataJPA
 * @author ronin
 * @version V1.0
 * @since 2019/11/19 11:26
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringBoot_SpringDataJPATest {

    @Autowired
    private IUserDao userDao;

    /**
     * 注意:初次测试程序只进行了自动建表服务，并没有表里面添加数据,如果要进行测试,需要
     *      添加数据
     */
    @Test
    public void fun(){
        User user = userDao.findById("1").get();
        System.out.println(user);
    }


}
