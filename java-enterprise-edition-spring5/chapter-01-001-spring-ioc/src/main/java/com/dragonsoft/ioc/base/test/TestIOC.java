package com.dragonsoft.ioc.base.test;

import com.dragonsoft.ioc.base.domain.Car;
import com.dragonsoft.ioc.base.domain.Dog;
import com.dragonsoft.ioc.base.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestIOC {
    public static void main(String[] args) {
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
