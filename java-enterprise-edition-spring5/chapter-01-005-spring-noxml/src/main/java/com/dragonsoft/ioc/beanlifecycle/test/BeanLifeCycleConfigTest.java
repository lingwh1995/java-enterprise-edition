package com.dragonsoft.ioc.beanlifecycle.test;

import com.dragonsoft.ioc.beanlifecycle.config.BeanLifeCycleConfig;
import com.dragonsoft.ioc.beanlifecycle.domain.Car;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ronin
 */
public class BeanLifeCycleConfigTest {

   @Test
   public void fun1(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanLifeCycleConfig.class);
        Car car = context.getBean(Car.class);
        System.out.println(car);
        context.close();
   }
}
