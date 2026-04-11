package org.bluebridge.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    /**
     * 发送消息:使用字符串封装消息
     */
    public void produceByString() {
        kafkaTemplate.send("hello-topic","hello kafka send方法一个参数~");
    }

    /**
     * 发送消息:使用Message对象封装消息
     */
    public void produceByMessage() {
        Message<String> message = MessageBuilder.withPayload("hello kafka Message对象~")
                .setHeader(KafkaHeaders.TOPIC, "hello-topic")
                .build();
        kafkaTemplate.send(message);
    }

    /**
     * 发送消息:使用ProducerRecord对象封装消息
     */
    public void produceByProducerRecord() {
        //存放一些信息，消费者收到该消息后，可以拿到Headers中存放的信息
        Headers headers = new RecordHeaders();
        headers.add("phone","15291183358".getBytes());
        headers.add("address","北京".getBytes());
        //String topic, Integer partition, Long timestamp, K key, V value, Iterable<Header> headers
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(
                "hello-topic",
                0,
                System.currentTimeMillis(),
                "key",
                "hello kafka ProducerRecord对象~",
                headers);
        kafkaTemplate.send(producerRecord);
    }

    /**
     * 发送消息:使用send方法参数封装消息
     */
    public void produceBySendFunctionParams() {
        //String topic, Integer partition, Long timestamp, K key, @Nullable V data
        kafkaTemplate.send(
                "hello-topic",
                0,
                System.currentTimeMillis(),
                "key",
                "hello kafka send方法多个参数~");
    }

    /**
     * 发送消息:使用sendDefault方法参数封装消息
     *      注意:需要在yml文件中配置默认主题 spring.template.default-topic
     */
    public void produceBysSendDefaultFunction() {
        //Integer partition, Long timestamp, K key, V data
        kafkaTemplate.sendDefault(0,
                System.currentTimeMillis(),
                "key",
                "hello kafka sendDefault方法多个参数");
    }
}
