package org.bluebridge.circulardependence.injectbyset.prototype;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringTest.class);

    /**
     * 测试 prototype + setter模式
     * 结论：会出现循环依赖问题
     *      会报这个异常  BeanCurrentlyInCreationException
     *      错误日志    Requested bean is currently in creation: Is there an unresolvable circular reference?
     */
    @Test
    public void testPrototypeScopeAndSetterCircularDependence() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("injectbyset/applicationContext-prototype.xml");
        Wife wife = applicationContext.getBean("wife", Wife.class);
        logger.info(wife.toString());
        Husband husband = applicationContext.getBean("husband", Husband.class);
        logger.info(husband.toString());
    }
}
