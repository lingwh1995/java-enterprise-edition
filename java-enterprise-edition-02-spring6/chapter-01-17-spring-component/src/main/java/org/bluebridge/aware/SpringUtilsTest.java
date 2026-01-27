package org.bluebridge.aware;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bluebridge.datasource.SpringTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lingwh
 * @desc Spring 工具类测试类
 * @date 2026/1/10 12:01
 */
public class SpringUtilsTest {

    private static final Logger logger = LogManager.getLogger(SpringTest.class);

    /**
     * 测试 Spring 工具类
     */
    @Test
    public void testSpringUtils() {
        // 加载配置文件并启动Spring容器，没有这一步，Spring容器没有启动起来
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-aware.xml");
        for (String beanDefinitionName : SpringUtils.getBeanDefinitionNames()) {
            logger.info("beanDefinitionName: " + beanDefinitionName);
        }
    }

}
