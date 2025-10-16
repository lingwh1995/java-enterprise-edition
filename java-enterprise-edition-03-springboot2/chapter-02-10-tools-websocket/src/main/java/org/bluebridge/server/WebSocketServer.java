package org.bluebridge.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Component
@ServerEndpoint("/websocket")
public class WebSocketServer {
    
    // 存储连接
    private static Map<String, Session> sessions = new ConcurrentHashMap<>();
    
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        sessions.put(userId, session);
        log.info("用户 {} 链接成功", userId);
    }
    
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("收到消息: {}", message);
        // 回显消息
        session.getBasicRemote().sendText("服务器收到: " + message);
    }
    
    @OnClose
    public void onClose(@PathParam("userId") String userId) {
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
