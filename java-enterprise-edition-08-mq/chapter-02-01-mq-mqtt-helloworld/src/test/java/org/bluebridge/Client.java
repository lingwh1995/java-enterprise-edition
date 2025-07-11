package org.bluebridge;

import org.bluebridge.consumer.MqttMessageConsumer;
import org.bluebridge.provider.MqttMessageProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Client {

    /**
     * mqtt消息提供者
     */
    @Resource
    private MqttMessageProvider mqttMessageProvider;

    /**
     * mqtt消息消费者
     */
    @Resource
    private MqttMessageConsumer mqttMessageConsumer;

    @Test
    public void sendMessage() {
        mqttMessageProvider.sendMessage("hello mqtt! I am springboot!", "test/hello-topic");
    }
}
