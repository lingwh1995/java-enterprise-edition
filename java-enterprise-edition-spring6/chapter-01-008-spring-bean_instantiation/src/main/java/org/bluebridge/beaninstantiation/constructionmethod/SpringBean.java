package org.bluebridge.beaninstantiation.constructionmethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Spring示例化bean的第一种方式： 使用构造方法实例化bean
 */
public class SpringBean {
    private static final Logger logger = LoggerFactory.getLogger(SpringBean.class);

    public SpringBean() {
        logger.info("springBean的无参构造方法执行了...");
    }
}
