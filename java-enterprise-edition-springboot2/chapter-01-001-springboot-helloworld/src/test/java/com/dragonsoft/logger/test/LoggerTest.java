package com.dragonsoft.logger.test;

import com.dragonsoft.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ronin
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class LoggerTest {

    Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    /**
     * 1.设置日志级别
     *  SpringBoot默认日志级别是info级别的,要改变这个级别:
     *      在全局配置文件中配置logger.level
     *      注意:在设置日志输出级别的时候要规定包的范围
     * 2.设置路径输出到哪个文件
     *  logging.file
     * 3.设置文件输出的路径
     *  logging.path
     *  注意:一般设置了path就可以了,不用再设置file了
     * 4.设置控制台输出的格式:
     *  logging.pattern.console
     */
    @Test
    public void fun1(){
        System.out.println(logger.getClass());
        logger.trace("trace日志");
        logger.debug("debug日志");
        logger.info("info日志");
        logger.warn("warning日志");
        logger.error("error日志");
    }
}
