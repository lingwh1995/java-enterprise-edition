package org.bluebridge.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 关键注解说明
 *    @ServerEndpoint: 定义WebSocket端点路径，支持路径参数
 *    @OnOpen: 连接建立时执行的方法
 *    @OnMessage: 接收消息时执行的方法
 *    @OnClose: 连接关闭时执行的方法
 *    @OnError: 发生错误时执行的方法
 *    @PathParam: 获取URL中的路径参数
 *
 * 核心API
 *    Session: 表示一个WebSocket会话，用于发送消息
 *    @PathParam: 获取连接URL中的参数
 *    BasicRemote: 用于同步发送消息
 *    AsyncRemote: 用于异步发送消息
 */

/**
 * 服务端端点
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{userId}")
public class WebSocketServer {

    private static Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        sessions.put(userId, session);
        log.info("用户 {} 连接成功", userId);
    }

    @OnMessage
    public void onMessage(Session session, @PathParam("userId") String userId, String message) throws IOException {
        // 解析消息
        String messageType = message.substring(0, 2);
        switch (messageType) {
            case "01":
                // 普通消息
                log.info("用户 {} 发送普通消息: {}", userId, message);
                // 发送普通消息
                session.getBasicRemote().sendText("[普通消息]: " + message);
                break;
            case "02":
                // 定向消息
                String targetUserId = message.substring(2, 6);
                log.info("用户 {} 发送定向消息 => {}，消息内容: {}", userId, targetUserId, message);
                String messageContent = message.substring(6);
                // 发送消息给目标用户
                messageContent = "[定向消息 => " + targetUserId + "]: " + messageContent;
                sendMessage(targetUserId, messageContent);
                break;
            case "03":
                // 广播消息
                log.info("用户 {} 发送广播消息: {}", userId, message);
                // 遍历所有用户，发送消息
                for (Map.Entry<String, Session> entry : sessions.entrySet()) {
                    Session targetSession = entry.getValue();
                    if (targetSession.isOpen()) {
                        targetSession.getBasicRemote().sendText("[广播消息]: " + message);
                    }
                }
                break;
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId) {
        sessions.remove(userId);
        log.info("用户 {} 断开连接", userId);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误: {}", error.getMessage());
    }

    // 发送消息给指定用户
    public void sendMessage(String userId, String message) throws IOException {
        Session session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            session.getBasicRemote().sendText(message);
        }
    }

}