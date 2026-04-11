package org.bluebridge.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;

import javax.annotation.Resource;

/**
 * 接收入站消息的处理器
 */
@Slf4j
@MessageEndpoint
public class MqttMessageReceiverHandler {

    @Resource
    private MqttMessageSenderHandler mqttMessageSenderHandler;

    /**
     * 处理接收到的消息
     * @param message
     */
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(Message<?> message) {
        log.info("收到的完整消息为--->{}", message);
        String topic = (String) message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC);
        String payload = message.getPayload().toString();
        Integer qos = (Integer) message.getHeaders().get(MqttHeaders.RECEIVED_QOS);
        log.info("Received message from topic '{}', QoS: {}, payload: {}", topic, qos, payload);

        // 根据主题处理不同的消息
        handleByTopic(topic, payload);
    }

    /**
     * 根据主题处理消息
     * @param topic 主题
     * @param payload 消息内容
     */
    private void handleByTopic(String topic, String payload) {
        if (topic.endsWith("test/hello-topic")) {
            log.info("Processing hello topic message: {}", payload);
            String replyPayload = "Hello, mqtt!";
            mqttMessageSenderHandler.sendMessage(replyPayload, topic.concat("/reply"));
        } else {
            log.info("Processing general message from topic '{}': {}", topic, payload);
        }
    }

}