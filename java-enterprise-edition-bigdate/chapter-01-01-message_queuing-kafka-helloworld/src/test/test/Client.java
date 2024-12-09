package org.bluebridge.test;

import org.bluebridge.producer.EventProducer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
