package org.bluebridge.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@MessageEndpoint
public class MqttMessageConsumer {

    // 处理接收到的消息
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(Message<?> message) {
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
        } else if (topic.contains("temperature")) {
            log.info("Processing temperature data: {}", payload);
        } else if (topic.contains("humidity")) {
            log.info("Processing humidity data: {}", payload);
        } else {
            log.info("Processing general message from topic '{}': {}", topic, payload);
        }
    }

}