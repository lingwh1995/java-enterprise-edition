package org.bluebridge.client;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;

/**
 * @author lingwh
 * @desc 客户端端点2
 * @date 2025/10/17 16:42
 */
@Slf4j
@ClientEndpoint
public class WebSocketClient_USER_0002 {

    private static final String USER_ID = "0002";
    private static final String WS_URL = "http://127.0.0.1:8080/websocket-spring-sockjs";
    private static final String FULL_WS_URL = WS_URL + USER_ID;

    /**
     * 测试数据
     *    普通消息  01Hello
     *    定向消息  020002Hello => 发给 0003 用户
     *    广播消息  03Hello
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 连接服务器（注意：实际运行需部署服务器到 8080 端口）
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        Session session = container.connectToServer(WebSocketClient_USER_0002.class,
                new URI(FULL_WS_URL));

        // 从控制台输入消息并发送
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请输入消息（输入 exit 退出）：\n");
            String message = scanner.nextLine();
            if ("exit".equals(message)) {
                session.close();
                break;
            }
            // 发送消息给服务器
            session.getBasicRemote().sendText(message);
        }
        scanner.close();
    }

    /**
     * 连接建立时触发
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        log.info("用户 {} 连接成功", USER_ID);
    }

    /**
     * 收到服务器消息时触发
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("用户 {} 收到服务器回复：{}", USER_ID, message);
    }

    /**
     * 连接关闭时触发
     * @param reason
     */
    @OnClose
    public void onClose(CloseReason reason) {
        log.info("连接关闭，原因： {}", reason.getReasonPhrase());
    }

    /**
     * 发生错误时触发
     * @param error
     */
    @OnError
    public void onError(Throwable error) {
        log.info("客户端错误： {}", error.getMessage());
    }

}

