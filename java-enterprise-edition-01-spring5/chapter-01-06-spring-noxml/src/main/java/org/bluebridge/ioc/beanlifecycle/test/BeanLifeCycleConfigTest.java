package org.bluebridge.ioc.beanlifecycle.test;

import org.bluebridge.ioc.beanlifecycle.config.BeanLifeCycleConfig;
import org.bluebridge.ioc.beanlifecycle.domain.Car;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/10 11:32
 */
public class BeanLifeCycleConfigTest {

   @Test
   public void testBeanLifeCycleConfig(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanLifeCycleConfig.class);
        Car car = context.getBean(Car.class);
        System.out.println(car);
        context.close();
   }
}
