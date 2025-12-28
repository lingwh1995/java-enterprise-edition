package org.bluebridge.common.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.bluebridge.common.util.JsonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author lingwh
 * @desc redis序列化配置
 * @date 2025/11/22 17:40
 */
@Configuration
public class RedisConfig {

    /**
     * 配置RedisTemplate实例，用于操作Redis数据库
     * @param connectionFactory Redis连接工厂
     * @return 配置好的RedisTemplate实例
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        // 创建RedisTemplate实例
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        
        // 获取全局共享的ObjectMapper并配置默认类型映射
        ObjectMapper objectMapper = JsonUtils.createMapper();
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        
        // 创建基于Jackson的JSON序列化器用于序列化value值
        GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
        template.setValueSerializer(valueSerializer);
        
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        template.setKeySerializer(keySerializer);
        
        // 初始化template属性
        template.afterPropertiesSet();
        
        // 设置hash数据结构的key和value序列化器
        template.setHashKeySerializer(keySerializer);
        template.setHashValueSerializer(valueSerializer);
        
        return template;
    }

}
