package org.bluebridge.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URI;
import java.util.Map;

/**
 * WebSocket握手拦截器，用于获取路径中的userId参数
 */
@Slf4j
public class ParamsInterceptor implements HandshakeInterceptor {

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
        String[] urlSplit = url.split("/");
        String userId = urlSplit[urlSplit.length - 1];
        attributes.put("userId", userId);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // 握手后操作（可选）
    }

}
