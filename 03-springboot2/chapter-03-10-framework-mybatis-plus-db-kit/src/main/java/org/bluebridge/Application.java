package org.bluebridge;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot 启动类
 *
 * @author lingwh
 * @date 2025/2/27 08:40
 */
@SpringBootApplication
@MapperScan("org.bluebridge")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
