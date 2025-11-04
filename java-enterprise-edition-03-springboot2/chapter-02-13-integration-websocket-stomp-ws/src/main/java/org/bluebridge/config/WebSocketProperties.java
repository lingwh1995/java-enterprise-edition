package org.bluebridge.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author lingwh
 * @desc WebSocket配置属性
 * @date 2025/10/18 18:25
 */
@Configuration
@ConfigurationProperties(prefix = "spring.websocket")
@Data
public class WebSocketProperties {

    // websocket端点路径前缀
    private String endpointPathPrefix;
    // 允许的跨域来源
    private List<String> allowedOrigins;
    // 是否启用sockjs
    private boolean enableSockjs;

}

