package org.bulebridge.ioc.value.config;

import org.bulebridge.ioc.value.domain.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author ronin
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
