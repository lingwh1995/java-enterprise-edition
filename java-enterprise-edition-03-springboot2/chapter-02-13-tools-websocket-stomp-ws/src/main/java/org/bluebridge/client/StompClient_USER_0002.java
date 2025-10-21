package org.bluebridge.client;

import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

/**
 * @author lingwh
 * @desc Stomp版WebSocket客户端2
 * @date 2025/10/21 11:13
 */
public class StompClient_USER_0002 {

    private static final String USER_ID = "0002";
    private static final String WS_URL = "ws://localhost:8080";
    private static final String ENDPOINT_PATH_PREFIX = "/websocket-stomp-ws";
    private static final String BACKSLASH = "/";
    private static final String FULL_WS_URL = WS_URL + ENDPOINT_PATH_PREFIX + BACKSLASH + USER_ID;

    /**
     * 测试数据
     *    普通消息  01Hello
     *    定向消息  020002Hello => 发给 0002 用户
     *    广播消息  03Hello
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) {
        /**
         * 原生WebSocket客户端
         */
        // 创建WebSocket客户端
        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        // 创建WebSocket STOMP客户端
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(webSocketClient);


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
        // 创建 STOMP 客户端
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(sockJsClient);
        */

        // 添加消息转换器
        webSocketStompClient.setMessageConverter(new StringMessageConverter());

        // 创建STOMP会话处理器
        StompSessionHandler sessionHandler = new MyStompSessionHandler(USER_ID, ENDPOINT_PATH_PREFIX);
        // 连接到STOMP端点
        webSocketStompClient.connect(FULL_WS_URL, sessionHandler);
    }

}


