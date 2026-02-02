package org.bluebridge;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.bluebridge.mapper")
@EnableMethodCache(basePackages = "org.bluebridge.service")  // 启用方法缓存
@EnableCreateCacheAnnotation  // 启用创建缓存注解(可选)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}