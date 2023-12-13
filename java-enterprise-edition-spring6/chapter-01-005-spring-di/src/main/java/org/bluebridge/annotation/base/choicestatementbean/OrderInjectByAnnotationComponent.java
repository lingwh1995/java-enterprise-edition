package org.bluebridge.annotation.base.choicestatementbean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderInjectByAnnotationComponent {

    private static final Logger logger = LoggerFactory.getLogger(OrderInjectByAnnotationComponent.class);

    public OrderInjectByAnnotationComponent() {
        logger.info("被@Component注解标注的类的无参数构造执行了...");
    }
}
