package org.bluebridge.annotation.base.choicestatementbean.choicestatement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class OrderInjectByAnnotationRepository {

    private static final Logger logger = LogManager.getLogger(OrderInjectByAnnotationRepository.class);

    public OrderInjectByAnnotationRepository() {
        logger.info("被@Repository注解标注的类的无参数构造执行了...");
    }
}
