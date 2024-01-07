package org.bluebridge.annotation.base.choicestatementbean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class OrderInjectByAnnotationController {

    private static final Logger logger = LoggerFactory.getLogger(OrderInjectByAnnotationController.class);

    public OrderInjectByAnnotationController() {
        logger.info("被@Controller注解标注的类的无参数构造执行了...");
    }
}
