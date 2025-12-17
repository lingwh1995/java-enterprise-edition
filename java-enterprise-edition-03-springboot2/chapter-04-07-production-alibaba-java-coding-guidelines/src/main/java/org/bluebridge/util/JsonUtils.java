package org.bluebridge.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * JSON工具类
 * 提供JSON序列化和反序列化功能
 * 
 * @author lingma
 * @since 1.0.0
 */
public class JsonUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    static {
        // 配置ObjectMapper
        configureObjectMapper(objectMapper);
    }
    
    /**
     * 配置ObjectMapper默认设置
     * 
     * @param mapper ObjectMapper实例
     */
    private static void configureObjectMapper(ObjectMapper mapper) {
        // 序列化时忽略空值属性
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        
        // 反序列化时忽略未知属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        
        // 注册JavaTime模块以支持JSR310时间类型
        mapper.registerModule(new JavaTimeModule());
        
        // 设置日期格式
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.setTimeZone(TimeZone.getDefault());
    }
    
    /**
     * 将对象转换为JSON字符串
     * 
     * @param object 待转换的对象
     * @return JSON字符串
     * @throws RuntimeException 转换失败时抛出
     */
    public static String toJson(Object object) {
        if (object == null) {
            return null;
        }
        
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("对象转JSON失败: {}", object, e);
            throw new RuntimeException("对象转JSON失败", e);
        }
    }
    
    /**
     * 将JSON字符串转换为指定类型的对象
     * 
     * @param json JSON字符串
     * @param clazz 目标类型Class
     * @param <T> 泛型类型
     * @return 转换后的对象
     * @throws RuntimeException 转换失败时抛出
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.error("JSON转对象失败: {}", json, e);
            throw new RuntimeException("JSON转对象失败", e);
        }
    }
    
    /**
     * 将JSON字符串转换为复杂泛型类型对象
     * 
     * @param json JSON字符串
     * @param typeReference 泛型类型引用
     * @param <T> 泛型类型
     * @return 转换后的对象
     * @throws RuntimeException 转换失败时抛出
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            logger.error("JSON转复杂类型对象失败: {}", json, e);
            throw new RuntimeException("JSON转复杂类型对象失败", e);
        }
    }
    
    /**
     * 将JSON字符串转换为复杂泛型类型对象
     * 
     * @param json JSON字符串
     * @param javaType Java类型
     * @param <T> 泛型类型
     * @return 转换后的对象
     * @throws RuntimeException 转换失败时抛出
     */
    @SuppressWarnings("unused")
    public static <T> T fromJson(String json, JavaType javaType) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        
        try {
            return objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            logger.error("JSON转复杂类型对象失败: {}", json, e);
            throw new RuntimeException("JSON转复杂类型对象失败", e);
        }
    }
    
    /**
     * 创建泛型类型
     * 
     * @param parametrized 泛型类
     * @param parameterClasses 泛型参数类型
     * @return JavaType
     */
    public static JavaType createJavaType(Class<?> parametrized, Class<?>... parameterClasses) {
        return objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }
    
    /**
     * 获取ObjectMapper实例（用于特殊配置需求）
     * 
     * @return ObjectMapper实例
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper.copy();
    }
    
    /**
     * 使用自定义ObjectMapper将对象转换为JSON字符串
     * 
     * @param object 待转换的对象
     * @param customMapper 自定义ObjectMapper
     * @return JSON字符串
     */
    public static String toJsonWithCustomMapper(Object object, ObjectMapper customMapper) {
        if (object == null) {
            return null;
        }
        
        try {
            return customMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("对象转JSON失败: {}", object, e);
            throw new RuntimeException("对象转JSON失败", e);
        }
    }
    
    /**
     * 使用自定义ObjectMapper将JSON字符串转换为指定类型的对象
     * 
     * @param json JSON字符串
     * @param clazz 目标类型Class
     * @param customMapper 自定义ObjectMapper
     * @param <T> 泛型类型
     * @return 转换后的对象
     */
    public static <T> T fromJsonWithCustomMapper(String json, Class<T> clazz, ObjectMapper customMapper) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        
        try {
            return customMapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.error("JSON转对象失败: {}", json, e);
            throw new RuntimeException("JSON转对象失败", e);
        }
    }
}
