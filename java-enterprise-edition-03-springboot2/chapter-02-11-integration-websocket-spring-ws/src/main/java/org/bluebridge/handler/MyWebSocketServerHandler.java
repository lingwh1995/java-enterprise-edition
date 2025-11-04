package org.bluebridge.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lingwh
 * @desc WebSocket处理器
 * @date 2025/10/18 14:10
 */
@Slf4j
@Component
public class MyWebSocketServerHandler extends TextWebSocketHandler {

    // 添加SESSION_ID和USER_ID的映射
    private static final Map<String, String> ONLINE_SESSION_ID_USER_ID_POOL = new ConcurrentHashMap<>();
    // 添加SESSION_ID和SESSION的映射
    private static final Map<String, WebSocketSession> ONLINE_SESSION_ID_SESSION_POOL = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 从attributes中获取userId
        String userId = (String) session.getAttributes().get("userId");
        if (userId != null) {
            ONLINE_SESSION_ID_USER_ID_POOL.put(session.getId(), userId);
            ONLINE_SESSION_ID_SESSION_POOL.put(session.getId(), session);
            log.info("用户 {} 建立连接: {}", userId, session.getId());
            log.info("当前在线用户数: {}", getOnlineCount());
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userId = ONLINE_SESSION_ID_USER_ID_POOL.get(session.getId());
        String payload = message.getPayload();
        log.info("收到用户 {} 的消息: {}", userId, payload);
        // 解析消息
        String messageType = payload.substring(0, 2);
        switch (messageType) {
            case "01":
                // 普通消息
                log.info("用户 {} 发送普通消息: {}", userId, payload);
                // 发送普通消息
                if(session.isOpen()) {
                    session.sendMessage(new TextMessage("[普通消息]: " + payload + "来自服务器"));
                }
                break;
            case "02":
                // 定向消息目标用户
                String targetUserId = payload.substring(2, 6);
                log.info("用户 {} 发送定向消息 => {}，消息内容: {}", userId, targetUserId, payload);
                String messageContent = payload.substring(6);
                // 发送消息给目标用户
                messageContent = "[定向消息 " + userId + " => " + targetUserId + "]: " + messageContent;
                // 根据userId获取sessionId
                String targetSessionId = ONLINE_SESSION_ID_USER_ID_POOL.entrySet().stream()
                        .filter(entry -> entry.getValue().equals(targetUserId))
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .get();

                if (targetSessionId != null) {
                    // 获取WebSocketSession（需要维护sessionId到WebSocketSession的映射）
                    WebSocketSession targetSession = ONLINE_SESSION_ID_SESSION_POOL.get(targetSessionId);
                    if (targetSession != null && targetSession.isOpen()) {
                        targetSession.sendMessage(new TextMessage(messageContent));
                    }
                }
                break;
            case "03":
                // 广播消息
                log.info("用户 {} 发送广播消息: {}", userId, message);
                // 遍历所有用户，发送消息
                for (Map.Entry<String, WebSocketSession> entry : ONLINE_SESSION_ID_SESSION_POOL.entrySet()) {
                    WebSocketSession targetSession = entry.getValue();
                    if (targetSession.isOpen()) {
                        targetSession.sendMessage(new TextMessage("[广播消息 " + userId + " => ]: " + payload));
                    }
                }
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = ONLINE_SESSION_ID_USER_ID_POOL.remove(session.getId());
        ONLINE_SESSION_ID_SESSION_POOL.remove(session.getId());
        log.info("用户 {} 连接关闭: {}", userId, session.getId());
        log.info("当前在线用户数: {}", getOnlineCount());
    }

    /**
     * 发生错误时触发
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("用户 {} 错误: {}", ONLINE_SESSION_ID_USER_ID_POOL.get(session.getId()), exception.getMessage());
    }

    /**
     * 获取当前在线用户数（线程安全的同步方法）
     * @return
     */
    public synchronized int getOnlineCount() {
        return ONLINE_SESSION_ID_USER_ID_POOL.size();
    }

}