package org.bluebridge.config;

import org.bluebridge.handler.MyWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author lingwh
 * @desc
 * @date 2025/10/16 18:32
 */
@Configuration
// 表示启用 WebSocket 消息代理
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 可选，注册SockJS处理器，兼容不支持WebSocket的浏览器
        registry.addHandler(new MyWebSocketHandler(), "/websocket-sockjs/{userId}")
                .addInterceptors(new ParamsInterceptor())
                .setAllowedOriginPatterns("http://*")
                .withSockJS();
    }

}