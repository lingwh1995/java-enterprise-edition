package org.bluebridge.client;

import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

/**
 * @author lingwh
 * @desc
 * @date 2025/10/21 10:32
 */
public class StompClient_USER_0001 {

    private static final String USER_ID = "0001";
    private static final String WS_URL = "ws://localhost:8080/websocket-stomp/";
    private static final String FULL_WS_URL = WS_URL + USER_ID;

    /**
     * 测试数据
     *    普通消息  01Hello
     *    定向消息  020002Hello => 发给 0002 用户
     *    广播消息  03Hello
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) {
        // 创建WebSocket客户端
        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        // 创建WebSocket STOMP客户端
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(webSocketClient);

        // 添加消息转换器
        webSocketStompClient.setMessageConverter(new StringMessageConverter());

        // 创建STOMP会话处理器
        StompSessionHandler sessionHandler = new MyStompSessionHandler(USER_ID);
        // 连接到STOMP端点
        webSocketStompClient.connect(FULL_WS_URL, sessionHandler);
    }

}


