package org.bluebridge.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bluebridge.domain.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    private static final  Logger logger = LogManager.getLogger(SpringTest.class);

    /**
     * 测试Spring 整合log4j2
     *  直接运行，控制台就已经可以出现debug级别的日志了
     */
    @Test
    public void testSpringLog4j2() {
        //1.启动Spring容器，并加载所有配置文件，并在容器中根据配置文件创建所有Bean
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        //2.根据Bean的id从容器中获取指定的Bean
        User user = applicationContext.getBean("user", User.class);
        System.out.println(user.getClass());
    }

    /**
     * 测试在Spring项目中使用log4j2记录日志
     *  直接运行，控制台就已经可以出现debug级别的日志了
     */
    @Test
    public void testApplicationUseLog4j2() {
        logger.info("我是info级别的日志消息......");
        logger.debug("我是debug级别的日志消息......");
        logger.error("我是error级别的日志消息......");
    }
}
