package com.dragonsoft.condition_family.test;

import com.dragonsoft.condition_family.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ronin
 * @version V1.0
 * @since 2019/11/14 10:59
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ConditionOnBeanTest {

    @Autowired(required =false)
    private Person person;

    @Test
    public void fun(){
        System.out.println(person);
    }
}
