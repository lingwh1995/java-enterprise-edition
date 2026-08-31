package org.bluebridge.controller;

import org.bluebridge.handler.MqttMessageSenderHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * MqttController
 *
 * @author lingwh
 * @date 2025/8/20 9:30
 */
@RestController
public class MqttController {

    @Resource
    private MqttMessageSenderHandler mqttMessageSenderHandler;

    /**
     * 访问 http://localhost:8080/send
     */
    @GetMapping("/send")
    public String sendTestMessage() {
        mqttMessageSenderHandler.sendMessage("hello mqtt! I am springboot!", "test/hello-topic");
        return "消息发送完成!";
    }
}