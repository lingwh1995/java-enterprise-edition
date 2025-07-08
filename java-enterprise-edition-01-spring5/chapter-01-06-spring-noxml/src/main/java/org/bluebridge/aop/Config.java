package org.bluebridge.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/5 12:35
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
