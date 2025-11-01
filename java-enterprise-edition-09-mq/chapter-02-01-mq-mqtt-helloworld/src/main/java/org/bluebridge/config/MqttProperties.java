package org.bluebridge.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author lingwh
 * @desc mqtt配置属性
 * @date 2025/8/20 9:25
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.mqtt")
public class MqttProperties {
    /**
     * 连接地址
     */
    private String brokerUrl;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
    /**
     * 连接超时时长
     */
    private Integer timeout;

    /**
     * 心跳时间
     */
    private Integer keepalive;

    /**
     * 遗嘱消息 QoS
     */
    private Integer qos;

    /**
     * 默认主题
     */
    private String defaultTopic;

    /**
     * 订阅主题
     */
    private List<String> topics;
}
