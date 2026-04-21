package org.bluebridge.beaninstantiation.constructionmethod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Spring示例化bean的第一种方式： 使用构造方法实例化bean
 */
public class SpringBean {
    private static final Logger logger = LogManager.getLogger(SpringBean.class);

    public SpringBean() {
        logger.info("springBean的无参构造方法执行了...");
    }
}
