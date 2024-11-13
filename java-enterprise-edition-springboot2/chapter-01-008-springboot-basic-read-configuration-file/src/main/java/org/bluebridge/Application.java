package org.bluebridge;

import org.bluebridge.configurationProperties_inject_by_configurationProperties.LibraryConfigInjectByEnableConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * SpringBoot注解驱动配置
 */
@SpringBootApplication
@EnableConfigurationProperties(LibraryConfigInjectByEnableConfigurationProperties.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
