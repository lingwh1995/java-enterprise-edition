package org.bluebridge.profile.base.bysetter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringTest {

    private static final Logger logger = LogManager.getLogger(SpringTest.class);

    /**
     * 测试使用setter方式为属性注入非引用数据类型的值
     */
    @Test
    public void testInjectValueBySetter(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/base/applicationContext-di-base.xml");
        UserInjectValueBySetter user = applicationContext.getBean("userInjectValueBySetter", UserInjectValueBySetter.class);
        logger.info(user.toString());
    }

}
