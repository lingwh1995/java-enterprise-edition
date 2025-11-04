package org.bluebridge.client;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.*;

import java.lang.reflect.Type;
import java.util.Scanner;

/**
 * @author lingwh
 * @desc stomp会话处理器
 * @date 2025/10/21 11:10
 */
@Slf4j
@AllArgsConstructor
public class MyStompSockjsSessionHandler extends StompSessionHandlerAdapter {

    // 接收外部传递来的用户id
    private String userId;
    // websocket端点路径前缀
    private String endpointPathPrefix;

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

        // 启动独立线程处理用户输入
        startInputThread(session);
    }

    private void startInputThread(StompSession session) {
        Thread inputThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.print("请输入消息（输入 exit 退出）：\n");
                    String message = scanner.nextLine();
                    if ("exit".equals(message)) {
                        session.disconnect();
                        break;
                    }
                    // 获取消息类型
                    String messageType = message.substring(0, 2);
                    switch (messageType) {
                        // 普通消息： 消息类型为00
                        case "01":
                            // 发送普通消息
                            session.send(endpointPathPrefix + "/chat/ordinary", message);
                            break;
                        // 定向消息： 消息类型为02
                        case "02":
                            // 发送定向消息
                            session.send(endpointPathPrefix + "/chat/private", message);
                            break;
                        // 广播消息： 消息类型为03
                        case "03":
                            // 发送广播消息
                            session.send(endpointPathPrefix + "/chat/broadcast", message);
                            break;
                        default:
                            log.info("未知消息类型：{}", messageType);
                            break;
                    }
                }
            } catch (Exception e) {
                log.error("输入处理异常", e);
            } finally {
                scanner.close();
            }
        });
        // 设置为守护线程
        inputThread.setDaemon(true);
        inputThread.start();
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
