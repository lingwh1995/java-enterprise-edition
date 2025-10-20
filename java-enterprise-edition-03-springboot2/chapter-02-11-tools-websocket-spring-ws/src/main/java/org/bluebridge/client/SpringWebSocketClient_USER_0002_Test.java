package org.bluebridge.client;


import java.net.URI;
import java.util.Scanner;

public class SpringWebSocketClient_USER_0002_Test {

    private static final String USER_ID = "0002";
    private static final String WS_URL = "ws://localhost:8080/websocket-spring-ws/";
    private static final String FULL_WS_URL = WS_URL + USER_ID;

    /**
     * 测试数据
     *    普通消息  01Hello
     *    定向消息  020003Hello => 发给 0003 用户
     *    广播消息  03Hello
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 服务端WebSocket地址（示例）
        URI webSocketUri = new URI(FULL_WS_URL);

        // 创建客户端并连接
        SpringWebSocketClient springWebSocketClient = new SpringWebSocketClient(webSocketUri);

        // 从控制台输入消息并发送
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请输入消息（输入 exit 退出）：\n");
            String message = scanner.nextLine();
            if ("exit".equals(message)) {
                // 关闭连接
                springWebSocketClient.close();
                break;
            }
            // 发送消息给服务器
            springWebSocketClient.sendMessage(message);
        }
        scanner.close();

        // 关闭连接
        springWebSocketClient.close();
    }

}
