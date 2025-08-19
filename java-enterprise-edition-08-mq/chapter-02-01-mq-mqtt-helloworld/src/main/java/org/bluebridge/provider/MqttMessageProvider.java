package org.bluebridge.provider;

import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MqttMessageProvider {

    @Resource
    private MessageChannel mqttOutboundChannel;

    public void sendMessage(String payload, String topic) {
        mqttOutboundChannel.send(MessageBuilder.withPayload(payload)
                // 动态主题
                .setHeader(MqttHeaders.TOPIC, topic)
                // QoS级别
                .setHeader(MqttHeaders.QOS, 1)
                .build());
    }

}