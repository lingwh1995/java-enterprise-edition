package org.bluebridge.common.config;

import org.bluebridge.common.constant.EnvironmentConstants;
import org.apache.ibatis.logging.nologging.NoLoggingImpl;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * @author lingwh
 * @desc
 * @date 2025/11/23 18:00
 */

@Configuration
public class MyBatisConfig {

    /** 获取当前运行环境 */
    @Resource
    private Environment environment;

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
