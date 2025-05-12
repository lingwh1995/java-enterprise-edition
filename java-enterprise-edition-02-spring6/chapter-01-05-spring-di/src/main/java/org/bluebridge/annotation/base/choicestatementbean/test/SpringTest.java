package org.bluebridge.annotation.base.choicestatementbean.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bluebridge.annotation.base.choicestatementbean.choicestatement.OrderInjectByAnnotationComponent;
import org.bluebridge.annotation.base.choicestatementbean.choicestatement.OrderInjectByAnnotationController;
import org.bluebridge.annotation.base.choicestatementbean.choicestatement.OrderInjectByAnnotationRepository;
import org.bluebridge.annotation.base.choicestatementbean.choicestatement.OrderInjectByAnnotationService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    private static final Logger logger = LogManager.getLogger(SpringTest.class);

    /**
     * 测试选择性实例化
     */
    @Test
    public void testInjectByAnnotationChoice() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("annotation/base/applicationContext-di-base-choicestatementbean.xml");
        OrderInjectByAnnotationComponent orderInjectByAnnotationComponent = applicationContext.getBean("orderInjectByAnnotationComponent", OrderInjectByAnnotationComponent.class);
        OrderInjectByAnnotationRepository orderInjectByAnnotationRepository = applicationContext.getBean("orderInjectByAnnotationRepository", OrderInjectByAnnotationRepository.class);
        OrderInjectByAnnotationService orderInjectByAnnotationService = applicationContext.getBean("orderInjectByAnnotationService", OrderInjectByAnnotationService.class);
        OrderInjectByAnnotationController orderInjectByAnnotationController = applicationContext.getBean("orderInjectByAnnotationController", OrderInjectByAnnotationController.class);
    }
}
