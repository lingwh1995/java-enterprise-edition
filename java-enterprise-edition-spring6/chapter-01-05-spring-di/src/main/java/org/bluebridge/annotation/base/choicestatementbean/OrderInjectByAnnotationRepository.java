package org.bluebridge.annotation.base.choicestatementbean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class OrderInjectByAnnotationRepository {

    private static final Logger logger = LoggerFactory.getLogger(OrderInjectByAnnotationRepository.class);

    public OrderInjectByAnnotationRepository() {
        logger.info("被@Repository注解标注的类的无参数构造执行了...");
    }
}
