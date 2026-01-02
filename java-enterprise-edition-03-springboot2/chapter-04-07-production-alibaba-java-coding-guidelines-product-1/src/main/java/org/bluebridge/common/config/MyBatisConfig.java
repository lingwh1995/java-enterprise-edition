package org.bluebridge.common.config;

import org.apache.ibatis.logging.nologging.NoLoggingImpl;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.bluebridge.common.constant.EnvironmentConstants;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


/**
 * @author lingwh
 * @desc
 * @date 2025/11/23 18:00
 */

@Configuration
public class MyBatisConfig {

    // 1. 使用 final 修饰符，确保变量不可变且必须在构造时初始化
    private final Environment environment;

    // 2. 构造函数注入：这是 Spring 官方最推荐、最健壮的方式
    // Spring Boot 启动时，如果发现只有这一个构造函数，会自动把 Environment 传进来
    public MyBatisConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            // 注册 LocalDateTime 等类型的别名
            configuration.getTypeAliasRegistry().registerAlias("LocalDateTime", java.time.LocalDateTime.class);
            configuration.getTypeAliasRegistry().registerAlias("LocalDate", java.time.LocalDate.class);
            configuration.getTypeAliasRegistry().registerAlias("LocalTime", java.time.LocalTime.class);
            String activeProfile = environment.getActiveProfiles()[0];
            // 根据当前环境决定启用什么日志实现，开发环境使用NoLoggingImpl.class，非开发环境则使用Slf4jImpl.class
            configuration.setLogImpl(EnvironmentConstants.DEV.equals(activeProfile) ? NoLoggingImpl.class : Slf4jImpl.class);
        };
    }

}
