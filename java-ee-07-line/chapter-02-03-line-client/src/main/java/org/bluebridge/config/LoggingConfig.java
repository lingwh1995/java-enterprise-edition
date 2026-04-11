package org.bluebridge.config;

import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lingwh
 * @desc 日志配置类
 * @date 2025/11/16 8:58
 */
@Configuration
public class LoggingConfig {

    @Bean
    public LoggingHandler loggingHandler() {
        return new LoggingHandler(LogLevel.DEBUG);
    }

}
