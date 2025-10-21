package org.bluebridge.client;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.*;

import java.lang.reflect.Type;
import java.util.Scanner;

/**
 * @author lingwh
 * @desc
 * @date 2025/10/21 11:10
 */
@Slf4j
@AllArgsConstructor
public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    // 接收外部传递来的用户id
    private String userId;

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("连接服务端成功，会话 ID：{}", session);

        /**
         * 订阅普通消息
         */
        session.subscribe("/user/" + userId + "/topic/ordinary", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                log.info("收到服务端普通消息：{}", payload);
            }
        });

        /**
         * 订阅点对点消息
         */
        session.subscribe("/user/" + userId + "/topic/private", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                log.info("收到服务端点对点消息：{}", payload);
            }
        });

        /**
         * 订阅广播消息
         */
        session.subscribe("/topic/broadcast", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                log.info("收到服务端广播消息：{}", payload);
            }
        });

        /**
         * 发送消息
         */
        // 从控制台输入消息并发送
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请输入消息（输入 exit 退出）：\n");
            String message = scanner.nextLine();
            if ("exit".equals(message)) {
                // 关闭连接
                session.disconnect();
                break;
            }
            // 发送消息给服务器
            // 获取消息类型
            String messageType = message.substring(0, 2);
            // 获取消息内容
            String messageContent = message.substring(2);
            switch (messageType) {
                // 普通消息： 消息类型为00
                case "01":
                    // 发送普通消息
                    session.send("/websocket-stomp/chat/ordinary", message);
                    break;
                // 定向消息： 消息类型为02
                case "02":
                    // 发送定向消息
                    session.send("/websocket-stomp/chat/private", message);
                    break;
                // 广播消息： 消息类型为03
                case "03":
                    // 发送广播消息
                    session.send("/websocket-stomp/chat/broadcast", message);
                    break;
                default:
                    log.info("未知消息类型：{}", messageType);
                    break;
            }
        }
        scanner.close();

        // 关闭连接
        session.disconnect();
    }

    @Override
    public void handleException(StompSession session, StompCommand command,
                                StompHeaders headers, byte[] payload, Throwable exception) {
        log.info("发生异常，异常信息：{}", exception.getMessage());
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        log.info("发生Transport异常，异常信息：{}", exception.getMessage());
    }

}
