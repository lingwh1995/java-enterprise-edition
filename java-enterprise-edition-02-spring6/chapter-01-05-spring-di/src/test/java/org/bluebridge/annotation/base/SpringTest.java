package org.bluebridge.annotation.base;

import org.bluebridge.annotation.base.choicestatementbean.OrderInjectByAnnotationComponent;
import org.bluebridge.annotation.base.choicestatementbean.OrderInjectByAnnotationController;
import org.bluebridge.annotation.base.choicestatementbean.OrderInjectByAnnotationRepository;
import org.bluebridge.annotation.base.choicestatementbean.OrderInjectByAnnotationService;
import org.bluebridge.annotation.base.statementbean.UserInjectByAnnotationComponent;
import org.bluebridge.annotation.base.statementbean.UserInjectByAnnotationController;
import org.bluebridge.annotation.base.statementbean.UserInjectByAnnotationRepository;
import org.bluebridge.annotation.base.statementbean.UserInjectByAnnotationService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringTest.class);

    /**
     * 测试使用注解方式为属性注入非引用数据类型的值，使用@Component注解注入
     */
    @Test
    public void testInjectByAnnotationComponent() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("annotation/base/applicationContext-di-base-statementbean.xml");
        UserInjectByAnnotationComponent user = applicationContext.getBean("userInjectByAnnotationComponent", UserInjectByAnnotationComponent.class);
        logger.info(user.toString());
    }

    /**
     * 测试使用注解方式为属性注入非引用数据类型的值，使用@Repository注解注入
     */
    @Test
    public void testInjectByAnnotationRepository() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("annotation/base/applicationContext-di-base-statementbean.xml");
        UserInjectByAnnotationRepository user = applicationContext.getBean("userInjectByAnnotationRepository", UserInjectByAnnotationRepository.class);
        logger.info(user.toString());
    }

    /**
     * 测试使用注解方式为属性注入非引用数据类型的值，使用@Service注解注入
     */
    @Test
    public void testInjectByAnnotationService() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("annotation/base/applicationContext-di-base-statementbean.xml");
        UserInjectByAnnotationService user = applicationContext.getBean("userInjectByAnnotationService", UserInjectByAnnotationService.class);
        logger.info(user.toString());
    }

    /**
     * 测试使用注解方式为属性注入非引用数据类型的值，使用@Controller注解注入
     */
    @Test
    public void testInjectByAnnotationController() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("annotation/base/applicationContext-di-base-statementbean.xml");
        UserInjectByAnnotationController user = applicationContext.getBean("userInjectByAnnotationController", UserInjectByAnnotationController.class);
        logger.info(user.toString());
    }

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
