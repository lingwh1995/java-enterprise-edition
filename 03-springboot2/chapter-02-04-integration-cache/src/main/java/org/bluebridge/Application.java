package org.bluebridge;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 开启基于注解的缓存
 *   @EnableCaching
 *
 * 使用基于注解的缓存
 *   @Cacheable ： 将方法缓存的结果缓存起来
 *   @CacheEvict
 *   @CachePut
 *
 * @author lingwh
 * @date 2019/11/18 9:05
 */
@EnableCaching
@MapperScan("org.bluebridge.dao")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
