package org.bluebridge;

import org.bluebridge.config.MqttProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * SpringBoot 启动类
 *
 * @author lingwh
 * @date 2025/8/20 16:08
 */
@SpringBootApplication
@EnableConfigurationProperties(MqttProperties.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
