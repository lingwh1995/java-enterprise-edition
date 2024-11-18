package org.bluebridge.config;

import org.bluebridge.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * 自定义RedisTempalte
 * @author ronin
 */
@Configuration
public class ApplicationConfig {

    /**
     * 自定义RedisTempalte,这个RedisTempate有自动将对象转换为Json格式字符串的功能+
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<Object, User> userRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object, User> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置默认的Json处理器,支持的Json处理器请查看RedisSerializer这个接口
            //如果不设置,Json转换使用的是JdkSerializationRedisSerializer
        Jackson2JsonRedisSerializer<User> serializer = new Jackson2JsonRedisSerializer(User.class);
        redisTemplate.setDefaultSerializer(serializer);
        return redisTemplate;
    }
 }
