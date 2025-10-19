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
 * WebSocket简介
 *    WebSocket 是一种在单个TCP连接上进行全双工通信的协议。WebSocket使得客户端和服务器之间的数据交换变得更加简单，允许服务端主动向客户端
 *    推送数据。在WebSocket API中，浏览器和服务器只需要完成一次握手，两者之间就直接可以创建持久性的连接，并进行双向数据传输。
 * WebSocket特性
 *    全双工通信：WebSocket允许客户端和服务器之间进行双向通信，数据可以在同一时间双向流动。这意味着服务器可以主动向客户端推送数据，而无需等待客户端发起请求。
 *    长连接：WebSocket连接一旦建立，就会保持打开状态，直到显式关闭。这种长连接特性减少了频繁建立和销毁连接的开销，提高了通信效率。
 *    轻量级头部：WebSocket数据帧采用紧凑的二进制格式，减少了不必要的头部信息，提高了数据传输效率。
 *    实时性：由于数据可以直接在已建立的连接上传输，WebSocket能够实现实时或接近实时的数据交互。 *
 *    跨域支持：与HTTP一样，WebSocket也允许跨域通信，只需服务器端设置相应的CORS（跨源资源共享）头即可
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

    // USER_ID和SESSION的映射
    private static Map<String, Session> ONLINE_USER_ID_SESSION_POOL = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        ONLINE_USER_ID_SESSION_POOL.put(userId, session);
        log.info("用户 {} 连接成功", userId);
        log.info("当前在线用户数: {}", getOnlineCount());
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
                Session targetSession = ONLINE_USER_ID_SESSION_POOL.get(targetUserId);
                if (targetSession != null && targetSession.isOpen()) {
                    targetSession.getBasicRemote().sendText(messageContent);
                }
                break;
            case "03":
                // 广播消息
                log.info("用户 {} 发送广播消息: {}", userId, message);
                // 遍历所有用户，发送消息
                for (Map.Entry<String, Session> entry : ONLINE_USER_ID_SESSION_POOL.entrySet()) {
                    targetSession = entry.getValue();
                    if (targetSession.isOpen()) {
                        targetSession.getBasicRemote().sendText("[广播消息]: " + message);
                    }
                }
                break;
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId) {
        ONLINE_USER_ID_SESSION_POOL.remove(userId);
        log.info("用户 {} 断开连接", userId);
        log.info("当前在线用户数: {}", getOnlineCount());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误: {}", error.getMessage());
    }

    /**
     * 获取当前在线用户数（线程安全的同步方法）
     * @return
     */
    public synchronized int getOnlineCount() {
        return ONLINE_USER_ID_SESSION_POOL.size();
    }

}