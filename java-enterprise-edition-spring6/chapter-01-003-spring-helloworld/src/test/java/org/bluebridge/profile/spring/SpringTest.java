package org.bluebridge.profile.spring;

import org.bluebridge.profile.domain.Cat;
import org.bluebridge.profile.domain.SpringHelloWorld;
import org.bluebridge.profile.domain.User;
import org.bluebridge.profile.reflect.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    /**
     * 测试Spring Helloworld程序
     */
    @Test
    public void testSpringHelloWorld() {
        //1.启动Spring容器，并加载所有配置文件，并在容器中根据配置文件创建所有Bean
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-springhelloworld.xml");
        //2.根据Bean的id从容器中获取指定的Bean
            //使用强制类型转换处理返回值
        SpringHelloWorld springHelloWorld = (SpringHelloWorld)applicationContext.getBean("springHelloWorld");
        System.out.println(springHelloWorld.getClass());
            //不使用强制类型转换处理返回值
        SpringHelloWorld springHelloWorld1 = applicationContext.getBean("springHelloWorld", SpringHelloWorld.class);
        System.out.println(springHelloWorld1.getClass());
    }

    /**
     * 测试Spring 一次性加载多个配置文件
     */
    @Test
    public void testSpringLoadMultipleConfigurationFile() {
        //1.启动Spring容器，并加载所有配置文件，并在容器中根据配置文件创建所有Bean
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-user.xml","applicationContext-cat.xml");
        //2.根据Bean的id从容器中获取指定的Bean
        User user = (User)applicationContext.getBean("user");
        System.out.println(user.getClass());
        Cat cat = (Cat)applicationContext.getBean("cat");
        System.out.println(cat.getClass());
    }

    /**
     * 测试Spring 创建bean时调用了无参构造函数
     */
    @Test
    public void testSpringInvokeNoConstructionMethod() {
        //1.启动Spring容器，并加载所有配置文件，并在容器中根据配置文件创建所有Bean
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-reflect.xml");
        //2.根据Bean的id从容器中获取指定的Bean
        Person person = (Person)applicationContext.getBean("person");
        System.out.println(person.getClass());
    }

    /**
     * 测试Spring BeanFactory
     * ApplicationContext接口的顶级父接口是BeanFactory接口，所以SpringIoC容器使用了工厂模式
     * SpringIoC容器实现    XML解析+工厂模式+反射机制
     */
    @Test
    public void testBeanFactory() {
        //1.启动Spring容器，并加载所有配置文件，并在容器中根据配置文件创建所有Bean
        BeanFactory applicationContext = new ClassPathXmlApplicationContext("applicationContext-user.xml");
        //2.根据Bean的id从容器中获取指定的Bean
        User user = (User)applicationContext.getBean("user");
        System.out.println(user.getClass());
    }
}
