package com.dragonsoft.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author ronin
 */
@EnableAspectJAutoProxy
@Configuration
public class Config {

    @Bean
    public Caculator caculator(){
        return new Caculator();
    }

    @Bean
    public LogAspect logAspect(){
        return new LogAspect();
    }
}
