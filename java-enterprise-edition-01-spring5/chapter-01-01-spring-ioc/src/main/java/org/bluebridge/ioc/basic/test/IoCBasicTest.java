package org.bluebridge.ioc.basic.test;

import org.bluebridge.ioc.basic.domain.Dog;
import org.bluebridge.ioc.basic.domain.Car;
import org.bluebridge.ioc.basic.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IoCBasicTest {

    @Test
    public void testIoCBasic() {
        /**
         * ApplicationContext  -- 在加载applicationContext.xml时候就会创建具体的Bean对象的实例，还提供了一些其他的功能
         */
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-basic.xml");
        // 通过工厂获得类
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.sayHello();

        /**
         * 构造方法注入
         */
        Car carConstructorInject = (Car) applicationContext.getBean("carConstructorInject");
        System.out.println(carConstructorInject);

        /**
         * 构造方法注入
         */
        Car carConstructorIndexInject = (Car) applicationContext.getBean("carConstructorIndexInject");
        System.out.println(carConstructorIndexInject);

        /**
         * Setter注入
         */
        Dog dogSetterInject = (Dog) applicationContext.getBean("dogSetterInject");
        System.out.println(dogSetterInject);
    }

}
