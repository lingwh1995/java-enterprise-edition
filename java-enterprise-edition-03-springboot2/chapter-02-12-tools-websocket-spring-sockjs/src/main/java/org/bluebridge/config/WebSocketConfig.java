package org.bluebridge.config;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.handler.MyWebSocketServerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Map;

/**
 * @author lingwh
 * @desc WebSocket配置类
 * @date 2025/10/18 18:32
 */
@Slf4j
@Configuration
// 表示启用 WebSocket 消息代理
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Resource
    private WebSocketProperties webSocketProperties;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 注册WebSocket处理器，允许跨域访问
        WebSocketHandlerRegistration registration = registry.addHandler(new MyWebSocketServerHandler(),
                        webSocketProperties.getEndpointPathPrefix() + "/{userId}")
                .addInterceptors(handshakeInterceptor())
                .setAllowedOriginPatterns(webSocketProperties.getAllowedOrigins().toArray(new String[0]));
        // 是否开启SockJs
        if(webSocketProperties.isEnableSockjs()){
            registration.withSockJS();
        }
    }

    @Bean
    public HandshakeInterceptor handshakeInterceptor() {
        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                           WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                // 获取完整的请求 URL（包含协议、域名、端口、路径、参数等）
                URI requestUri = request.getURI();
                // 例如：ws://localhost:8080/ws?userId=123
                String url = requestUri.toString();

                // 可以进一步解析 URL 的各个部分：
                // 协议（ws 或 wss）
                String scheme = requestUri.getScheme();
                // 域名/主机名（如 localhost）
                String host = requestUri.getHost();
                // 端口号（如 8080）
                int port = requestUri.getPort();
                // 路径（如 /ws）
                String path = requestUri.getPath();
                // 查询参数（如 userId=123）
                String query = requestUri.getQuery();
                log.info("完整URL：{}，协议：{}，主机名：{}，端口号：{}，路径：{}，查询参数：{}",
                        url, scheme, host, port, path, query);
                int userIdStartIndex = url.indexOf(webSocketProperties.getEndpointPathPrefix()) + webSocketProperties.getEndpointPathPrefix().length() + 1;
                String userId = url.substring(userIdStartIndex, userIdStartIndex + 4);
                attributes.put("userId", userId);
                return true;
            }

            @Override
            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                       WebSocketHandler wsHandler, Exception exception) {
                // 握手后操作（可选）
            }
        };
    }

}