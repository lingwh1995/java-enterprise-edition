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
    private String brokerUrl;
    private String clientId;
    private String username;
    private String password;
    private String defaultTopic;
    private List<String> topics;
}
