package org.bluebridge.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {

    @KafkaListener(topics = "hello-topic",groupId = "helloGroup")//使用监听的方式接收消息
    public void consumer(String event) {
        System.out.println("读取到的事件:" + event);
    }
}
