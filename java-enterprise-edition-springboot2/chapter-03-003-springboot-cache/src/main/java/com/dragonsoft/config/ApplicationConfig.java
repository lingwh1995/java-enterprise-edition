package com.dragonsoft.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;


/**
 * @author ronin
 */
@Configuration
public class ApplicationConfig {

    /**
     * 自定义的缓存主键生成策略
     * 如何查看自定义的key:进入如下方法断点查看
     *      org.springframework.cache.concurrent.ConcurrentMapCache#lookup(java.lang.Object)
     * @return
     */
    @Bean("myKeyGenerator")
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                return method.getName()+"["+ Arrays.asList(objects)+"]";
            }
        };
    }
}
