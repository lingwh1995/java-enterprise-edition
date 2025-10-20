package org.bluebridge.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.security.Principal;

@Controller
public class StompChatController {

    @Resource
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 处理普通消息
     * @param message
     * @return
     */
    @MessageMapping("/chat/ordinary")
    public void handleOrdinaryMessage(String message, Principal principal) {
        // 获取用户ID
        String userId = principal.getName();
        // 向给服务端发送消息的用户发送消息
        messagingTemplate.convertAndSendToUser(userId, "/topic/ordinary", message);
    }
    /**
     * 处理定向消息
     * @param message
     * @return
     */
    @MessageMapping("/chat/private")
    public void handlePrivateMessage(String message) {
        // 添加输入验证
        if (message == null || message.length() < 6) {
            // 处理错误情况
            return;
        }
        try {
            // 解析消息格式：02 + 目标用户ID + 消息内容
            String messageType = message.substring(0, 2);
            if (!"02".equals(messageType)) {
                // 消息类型不匹配
                return;
            }
            // 获取目标用户ID
            String targetUserId = message.substring(2, 6);
            String content = message.substring(6);
            // 向指定用户发送消息
            messagingTemplate.convertAndSendToUser(targetUserId, "/topic/private", content);
        } catch (Exception e) {
            // 添加异常处理
        }
    }

    /**
     * 处理广播消息
     * @param message
     * @return
     */
    @MessageMapping("/chat/broadcast")
    @SendTo("/topic/broadcast")
    public String handlePublicMessage(String message) {
        return message;
    }

}

