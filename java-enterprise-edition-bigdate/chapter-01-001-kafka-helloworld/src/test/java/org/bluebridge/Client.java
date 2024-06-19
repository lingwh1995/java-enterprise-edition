package org.bluebridge;

import org.bluebridge.producer.EventProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Client {

    @Autowired
    private EventProducer eventProducer;

    /**
     * 测试生产者
     *      使用String封装消息
     */
    @Test
    public void testProduceByString() {
        eventProducer.produceByString();
    }

    /**
     * 测试生产者
     *      使用Message对象封装消息
     */
    @Test
    public void testProduceByMessage() {
        eventProducer.produceByMessage();
    }

    /**
     * 测试生产者
     *      使用ProducerRecord封装消息
     */
    @Test
    public void testProduceByProducerRecord() {
        eventProducer.produceByProducerRecord();
    }

    /**
     * 测试生产者
     *      使用send方法参数封装消息
     */
    @Test
    public void testProduceBySendFunctionParams() {
        eventProducer.produceBySendFunctionParams();
    }

    /**
     * 测试生产者
     *      使用sendDefault方法封装消息
     */
    @Test
    public void testProduceBySendDefaultFunction() {
        eventProducer.produceBysSendDefaultFunction();
    }
}
