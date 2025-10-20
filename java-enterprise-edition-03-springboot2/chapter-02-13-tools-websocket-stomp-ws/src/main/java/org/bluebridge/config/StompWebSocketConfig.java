package org.bluebridge.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.net.URI;
import java.security.Principal;
import java.util.Map;
import java.util.stream.IntStream;

@Slf4j
@Configuration
// 注解开启使用STOMP协议来传输基于代理(message broker)的消息,这时控制器支持使用@MessageMapping,就像使用@RequestMapping一样
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 用户可以订阅来自以"/topic", "/user"为前缀的消息，点对点应配置一个/user消息代理，广播式应配置一个/topic消息代理
        config.enableSimpleBroker("/topic", "/user");
        // 客户端发送过来的消息，需要以"/websocket-stomp"为前缀，再经过Broker转发给响应的Controller
        config.setApplicationDestinationPrefixes("/websocket-stomp");

        // 配置用户目的地前缀，点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
        config.setUserDestinationPrefix("/user");
    }

    /**
     * 原生websocket版
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //路径"/websocket/{userId}"被注册为STOMP端点，对外暴露，客户端通过该路径接入WebSocket服务
        registry.addEndpoint("/websocket/{userId}")
            // 添加默认握手拦截器
            .setHandshakeHandler(defaultHandshakeHandler())
            // 添加自定义握手拦截器
            .addInterceptors(paramsInterceptor())
            .setAllowedOriginPatterns("*");
    }

    /**
     * 自定义握手拦截器，这里用来处理请求头信息
     * @return
     */
    @Bean
    public HandshakeInterceptor paramsInterceptor() {
        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                           WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                // 获取完整的请求 URL（包含协议、域名、端口、路径、参数等）
                URI requestUri = request.getURI();
                // 获取请求url
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
                String[] urlSplit = url.split("/");
                int userIdIndex = IntStream.range(0, urlSplit.length)
                        .filter(i -> urlSplit[i].equals("websocket-stomp"))
                        .findFirst()
                        .getAsInt();
                String userId = urlSplit[userIdIndex + 1];
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

    @Bean
    public DefaultHandshakeHandler defaultHandshakeHandler() {
        return new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                                              Map<String, Object> attributes) {
                // 获取完整的请求 URL（包含协议、域名、端口、路径、参数等）
                URI requestUri = request.getURI();
                // 获取请求url
                String url = requestUri.toString();
                // 获取 userId
                String[] urlSplit = url.split("/");
                String userId = urlSplit[urlSplit.length - 1];

                Principal principal = request.getPrincipal();
                if (principal == null && userId != null) {
                    // 创建基于userId信息的用户主体
                    principal = () -> userId;
                }
                return principal;
            }
        };
    }


    @Configuration
    public class WebSocketConfig {

        @Bean
        public WebSocketClient webSocketClient() {
            return new StandardWebSocketClient();
        }

        @Bean
        public WebSocketStompClient webSocketStompClient(WebSocketClient webSocketClient) {
            return new WebSocketStompClient(webSocketClient);
        }

    }

}

