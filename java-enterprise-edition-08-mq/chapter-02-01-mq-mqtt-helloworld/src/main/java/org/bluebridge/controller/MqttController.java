package org.bluebridge.controller;

import org.bluebridge.provider.MqttMessageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MqttController {

    @Resource
    private MqttMessageProvider mqttMessageProvider;

    @GetMapping("/send")
    public String sendTestMessage() {
        mqttMessageProvider.sendMessage("hello mqtt! I am springboot!", "test/hello-topic");
        return "消息发送完成!";
    }
}