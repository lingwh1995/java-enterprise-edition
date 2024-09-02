package com.dragonsoft;

import com.dragonsoft.controller.UserController;
import com.dragonsoft.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ronin
 * @version V1.0
 * @since 2019/11/18 14:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisAnnotationTest {

    @Autowired
    private UserController userController;

    /**
     * 测试@Cacheable注解
     */
    @Test
    public void fun1(){
        System.out.println("--------------------------------------------------");
        User user1 = userController.getUserByIdWithCacheable("003");
        System.out.println(user1);
        System.out.println("--------------------------------------------------");
        User user2 = userController.getUserByIdWithCacheable("003");
        System.out.println(user2);
        System.out.println("--------------------------------------------------");
    }

    /**
     * 测试@Caching注解
     */
    @Test
    public void fun2(){
        System.out.println("--------------------------------------------------");
        User user1 = userController.getUserByIdWithCaching("001");
        System.out.println(user1);
        System.out.println("--------------------------------------------------");
        User user2 = userController.getUserByUsernameWithCaching("zhangsan");
        System.out.println(user2);
        System.out.println("--------------------------------------------------");
    }
}
