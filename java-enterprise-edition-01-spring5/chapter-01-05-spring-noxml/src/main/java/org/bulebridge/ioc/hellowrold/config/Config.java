package org.bulebridge.ioc.hellowrold.config;

import org.bulebridge.ioc.hellowrold.domain.Person;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * @author ronin
 */
@Configuration
//代表扫描的包的路径是:com.dragonsoft,并且扫描的时候根据注解过滤掉被@Controller和@Service的bean,也可以自定义扫描过滤规则
//根据注解类型来确定哪些类要被IOC容器管理
@ComponentScan(value= "com.dragonsoft.ioc.hellowrold",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,
                classes = {Controller.class, Service.class})})
//根据Class类型来确定哪些类要被IOC容器管理
//@ComponentScan(value="com.dragonsoft.ioc.hellowrold",
//        includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
//                classes = {PersonDao.class, Person.class})},useDefaultFilters = false)
public class Config {

    /**
     * 默认：
     *      1.默认使用方法名作为id
     *      2.默认创建的bean是单例的
     * 也可以使用@Bean的name属性来指定id
     * @Scope：
     *      如果为单例:IOC容器初始化的过程会实例化bean
     *      如果为多例:IOC容器初始化的过程不会实例化bea
     * @return
     */
    @Scope("prototype")
    @Bean(name="person")
    public Person person(){
        System.out.println("给IOC容器中放值");
        return new Person("0001","zhangsan","29");
    }
}
