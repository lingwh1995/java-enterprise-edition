package com.dragonsoft;

import com.dragonsoft.configurationProperties_bind_with_out_bean.LibraryBindWithoutBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * SpringBoot注解驱动配置
 */
@SpringBootApplication
@EnableConfigurationProperties(LibraryBindWithoutBean.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
