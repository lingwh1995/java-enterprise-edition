package org.bulebridge.ioc.base.test;

import org.bulebridge.ioc.base.domain.Car;
import org.bulebridge.ioc.base.domain.Dog;
import org.bulebridge.ioc.base.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestIOC {
    @Test
    public void testIoC() {
        /**
         * ApplicationContext        -- 在加载applicationContext.xml时候就会创建具体的Bean对象的实例，还提供了一些其他的功能
         */
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-base.xml");
        // 通过工厂获得类:
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.sayHello();

        /**
         * 构造方法注入
         */
        Car carConstructor = (Car) applicationContext.getBean("car_constructor");
        System.out.println(carConstructor);

        /**
         * 构造方法注入
         */
        Car carConstructorIndex = (Car) applicationContext.getBean("car_constructor_index");
        System.out.println(carConstructorIndex);

        /**
         * Setter注入
         */
        Dog dogSetter = (Dog) applicationContext.getBean("dog_setter");
        System.out.println(dogSetter);
    }
}
