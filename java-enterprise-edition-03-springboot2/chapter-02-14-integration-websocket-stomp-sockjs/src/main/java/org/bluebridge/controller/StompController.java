package org.bluebridge.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * @author lingwh
 * @desc STOMP控制器类
 * @date 2025/10/19 10:12
 */

/**
 * 基于Stomp的WebSocket的开发两个重要注解
 *    @MessageMapping("/chat/broadcast") // @MessageMapping 和 @RequestMapping 功能类似，处理来自客户端的消息。
 *    @SendTo("/topic/broadcast") // 如果服务器接受到了消息，就会对订阅了 @SendTo 括号中的地址的客户端发送消息。
 */
@Slf4j
@Controller
public class StompController {

    @Resource
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 处理普通消息
     * @param message
     * @return
     */
    @MessageMapping("/chat/ordinary")
    public void handleOrdinaryMessage(Principal principal, String message) {
        // 获取用户ID
        String userId = principal.getName();
        log.info("用户 {} 发送普通消息: {}", userId, message);
        // 获取消息内容
        String messageContent = message.substring(2);
        // 向给服务端发送消息的用户发送消息
        messagingTemplate.convertAndSendToUser(userId, "/topic/ordinary", messageContent);
    }

    /**
     * 处理定向消息
     * @param message
     * @return
     */
    @MessageMapping("/chat/private")
    public void handlePrivateMessage(Principal principal, String message) {
        // 获取用户ID
        String userId = principal.getName();
        // 获取目标用户ID
        String targetUserId = message.substring(2, 6);
        // 获取消息内容
        String messageContent = message.substring(6);
        log.info("用户 {} 发送定向消息 => {}，消息内容: {}", userId, targetUserId, messageContent);

        // 发送消息给目标用户
        messageContent = "[定向消息 " + userId + " => " + targetUserId + "]: " + messageContent;
        messagingTemplate.convertAndSendToUser(targetUserId, "/topic/private", messageContent);
    }

    /**
     * 处理广播消息
     * @param message
     * @return
     */
    @MessageMapping("/chat/broadcast")
    @SendTo("/topic/broadcast")
    public String handlePublicMessage(Principal principal, String message) {
        // 获取用户ID
        String userId = principal.getName();
        log.info("用户 {} 发送广播消息: {}", userId, message);
        // 获取消息内容
        String messageContent = message.substring(2);
        return messageContent;
    }

}

