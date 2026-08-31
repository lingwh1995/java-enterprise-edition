package org.bluebridge.handler;

import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 发送入站消息的处理器
 *
 * @author lingwh
 * @date 2025/8/20 9:23
 */
@Component
public class MqttMessageSenderHandler {

    @Resource
    private MessageChannel mqttOutboundChannel;

    /**
     * 发送 mqtt 消息到指定主题
     *
     * @param payload 消息内容
     * @param topic 主题
     */
    public void sendMessage(String payload, String topic) {
        mqttOutboundChannel.send(MessageBuilder.withPayload(payload)
                // 动态主题
                .setHeader(MqttHeaders.TOPIC, topic)
                // QoS 级别
                .setHeader(MqttHeaders.QOS, 1)
                .build());
    }

    /**
     * 发送 mqtt 消息到默认主题
     *
     * @param payload 消息内容
     */
    public void sendMessage(String payload) {
        mqttOutboundChannel.send(MessageBuilder.withPayload(payload)
                // QoS 级别
                .setHeader(MqttHeaders.QOS, 1)
                .build());
    }

    /**
     * 发送 mqtt 消息到指定主题并设置 QoS 级别
     *
     * @param payload 消息内容
     * @param topic 主题
     * @param qos QoS级别 (0, 1, 2)
     */
    public void sendMessage(String payload, String topic, int qos) {
        mqttOutboundChannel.send(MessageBuilder.withPayload(payload)
                // 动态主题
                .setHeader(MqttHeaders.TOPIC, topic)
                // QoS 级别
                .setHeader(MqttHeaders.QOS, qos)
                .build());
    }
}
