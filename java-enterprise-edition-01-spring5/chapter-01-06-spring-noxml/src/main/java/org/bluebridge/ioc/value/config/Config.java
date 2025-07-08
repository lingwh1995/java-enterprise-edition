package org.bluebridge.ioc.value.config;

import org.bluebridge.ioc.value.domain.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author lingwh
 * @desc   零配置搭建Spring开发环境测试
 * @date   2019/4/3 14:32
 */
//使用@PropertySource读取外部配置文件中的K/V,保存到运行的环境变量中
@PropertySource(value={"classpath:/person.properties"})
@Configuration
public class Config {

    @Bean
    public Person person(){
        return new Person();
    }
}
