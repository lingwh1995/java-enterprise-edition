package org.bluebridge.annotation.base.choicestatementbean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderInjectByAnnotationService {

    private static final Logger logger = LoggerFactory.getLogger(OrderInjectByAnnotationService.class);

    public OrderInjectByAnnotationService() {
        logger.info("被@Service注解标注的类的无参数构造执行了...");
    }
}
