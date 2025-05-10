package org.bulebridge.ioc.beanlifecycle.test;

import org.bulebridge.ioc.beanlifecycle.config.BeanLifeCycleConfig;
import org.bulebridge.ioc.beanlifecycle.domain.Car;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ronin
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
