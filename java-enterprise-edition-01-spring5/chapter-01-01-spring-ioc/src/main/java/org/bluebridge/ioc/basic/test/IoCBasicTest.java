package org.bluebridge.ioc.basic.test;

import org.bluebridge.ioc.basic.domain.Dog;
import org.bluebridge.ioc.basic.domain.Car;
import org.bluebridge.ioc.basic.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lingwh
 * @desc
 * @date   2019/3/17 9:45
 */
public class IoCBasicTest {

    @Test
    public void testIoCBasic() {
        // 在加载applicationContext.xml时候就会创建具体的Bean对象的实例，还提供了一些其他的功能
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-basic.xml");
        // 通过工厂获得类
//        UserService userService = (UserService) applicationContext.getBean("userService");
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.sayHello();

        // 构造方法名称注入
        Car carConstructorInject = (Car) applicationContext.getBean("carConstructorArgNameInject");
        System.out.println(carConstructorInject);

        // 构造方法索引注入
        Car carConstructorIndexInject = (Car) applicationContext.getBean("carConstructorArgIndexInject");
        System.out.println(carConstructorIndexInject);

        // Setter注入
        Dog dogSetterInject = (Dog) applicationContext.getBean("dogSetterInject");
        System.out.println(dogSetterInject);
    }

}
