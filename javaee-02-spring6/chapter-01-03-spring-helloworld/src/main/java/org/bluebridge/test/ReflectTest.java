package org.bluebridge.test;

import org.bluebridge.reflect.Person;
import org.junit.Test;

public class ReflectTest {

    /**
     * 测试反射调用无参构造函数
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void testReflect() throws InstantiationException, IllegalAccessException {
        Person person = Person.class.newInstance();
        System.out.println(person);
    }
}
