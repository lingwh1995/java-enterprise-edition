package org.bluebridge.test;

import org.bluebridge.producer.EventProducer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 客户端
 *
 * @author lingwh
 * @date 2025/8/20 10:37
 */
@SpringBootTest
public class Client {

    @Autowired
    private EventProducer eventProducer;

    /**
     * 测试生产者
     */
    @Test
    public void testProducer() {
        eventProducer.produce();
    }
}
