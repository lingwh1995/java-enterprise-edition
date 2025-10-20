package org.bluebridge.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * @author lingwh
 * @desc WebSocket配置属性
 * @date 2025/10/20 14:26
 */

@Configuration
@ConfigurationProperties(prefix = "spring.websocket")
@Data
public class WebSocketProperties {

    // websocket端点路径前缀
    private String endpointPathPrefix = "/ws";
    // 允许的跨域来源
    private List<String> allowedOrigins = Arrays.asList("*");
    // 是否启用sockjs
    private boolean enableSockjs = true;

}

