package org.bluebridge.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author lingwh
 * @desc WebSocket客户端
 * @date 2025/10/18 14:23
 */
@Slf4j
public class SpringWebSocketClient {

    private final WebSocketSession session;

    /**
     * 初始化客户端并连接服务端
     * @param webSocketUri
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public SpringWebSocketClient(URI webSocketUri) throws ExecutionException, InterruptedException {
        /**
         * 原生WebSocket客户端
         */
        // 创建WebSocket客户端（基于标准JSR-356）
        StandardWebSocketClient client = new StandardWebSocketClient();
        // 连接服务端，指定消息处理器
        this.session = client.doHandshake(new MyWebSocketClientHandler(), webSocketUri.toString()).get();

        /**
         * SockJS版WebSocket客户端
         */
        /*
        // 创建SockJS客户端
        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        // 配置传输列表，包含多种传输方式以支持不同环境
        List<Transport> transports = new ArrayList<>();
        // 创建WebSocket客户端作为首选传输方式
        transports.add(new WebSocketTransport(webSocketClient));
        // 添加XHR流传输（备选方式）
        try {
            // 动态加载XhrStreamingTransport，避免类不存在的问题
            Class.forName("org.springframework.web.socket.sockjs.client.XhrStreamingTransport");
            transports.add((Transport) Class.forName("org.springframework.web.socket.sockjs.client.XhrStreamingTransport")
                    .getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            log.warn("无法加载XhrStreamingTransport，将使用其他传输方式");
        }
        // 添加RestTemplateXHR传输（备选方式）
        transports.add(new RestTemplateXhrTransport());

        SockJsClient sockJsClient = new SockJsClient(transports);
        this.session = sockJsClient.doHandshake(new MyWebSocketClientHandler(), webSocketUri.toString()).get();
        */
    }

    /**
     * 发送文本消息
     * @param message
     * @throws Exception
     */
    public void sendMessage(String message) throws Exception {
        if (session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }

    /**
     * 关闭连接
     * @throws Exception
     */
    public void close() throws Exception {
        session.close();
    }

}
