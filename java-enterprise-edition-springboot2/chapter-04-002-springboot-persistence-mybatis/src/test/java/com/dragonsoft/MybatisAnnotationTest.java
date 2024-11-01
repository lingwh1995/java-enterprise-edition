package com.dragonsoft;

import com.dragonsoft.dao.IUserDao;
import com.dragonsoft.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author ronin
 * @version V1.0
 * @since 2019/11/18 14:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisAnnotationTest {

    @Autowired
    private IUserDao userDao;

    @Test
    public void fun(){
        System.out.println("-----------------------------");
        System.out.println(userDao);
        User user = userDao.getUserById("002");
        System.out.println(user);
        System.out.println("-----------------------------");
        List<User> users = userDao.getUsers();
        users.forEach(item -> System.out.println(item));
        System.out.println("-----------------------------");
    }
}
