package org.bluebridge.profile.reflect;

import org.junit.jupiter.api.Test;

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
