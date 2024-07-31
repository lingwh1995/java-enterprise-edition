package com.dragonsoft.propertySource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//使用@PropertySource读取外部配置文件中的K/V,保存到运行的环境变量中
@PropertySource(value={"classpath:/user.properties"})
@Configuration  //一般用来声明配置类，可以使用 @Component注解替代，不过使用@Configuration注解声明配置类更加语义
public class Config {

    @Bean
    public User person(){
        return new User();
    }
}
