package org.bluebridge.config;

import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.bluebridge.protocol.Serializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lingwh
 * @desc 序列化配置类
 * @date 2025/10/25 12:36
 */
@Configuration
public class SerializerConfig {

    @Value("${netty.serializer.algorithm}")
    private String serializerAlgorithm;

    public Serializer.Algorithm getSerializerAlgorithm() {
        if(serializerAlgorithm == null) {
            return Serializer.Algorithm.Java;
        } else {
            return Serializer.Algorithm.valueOf(serializerAlgorithm);
        }
    }

    @Bean
    public LoggingHandler loggingHandler() {
        return new LoggingHandler(LogLevel.DEBUG);
    }

}
