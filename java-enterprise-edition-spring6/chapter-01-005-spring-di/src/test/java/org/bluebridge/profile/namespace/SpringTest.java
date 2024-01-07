package org.bluebridge.profile.namespace;

import org.bluebridge.profile.namespace.cnamespace.UserInjectValueByCNamespace;
import org.bluebridge.profile.namespace.pnamespace.UserInjectValueByPNamespace;
import org.bluebridge.profile.namespace.utilnamespace.MyDataSource3;
import org.bluebridge.profile.namespace.utilnamespace.MyDataSource2;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

public class SpringTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringTest.class);

    /**
     * set方式注入提升     P命名空间注入
     */
    @Test
    public void testInjectValueByPNamespace(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/namespace/applicationContext-di-profile-namespace-p.xml");
        UserInjectValueByPNamespace user = applicationContext.getBean("userInjectValueByPNamespace", UserInjectValueByPNamespace.class);
        logger.info(user.toString());
    }

    /**
     * 构造方式注入提升     C命名空间注入
     */
    @Test
    public void testInjectValueByCNamespace(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/namespace/applicationContext-di-profile-namespace-c.xml");
        UserInjectValueByCNamespace userByParamName = applicationContext.getBean("userInjectValueByCNamespaceByParamName", UserInjectValueByCNamespace.class);
        logger.info(userByParamName.toString());
        UserInjectValueByCNamespace userByParamIndex = applicationContext.getBean("userInjectValueByCNamespaceByParamIndex", UserInjectValueByCNamespace.class);
        logger.info(userByParamIndex.toString());
    }

    /**
     * Spring配置文件复用     util命名空间注入
     */
    @Test
    public void testUtilNamespace() throws SQLException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/namespace/applicationContext-di-profile-namespace-util.xml");
        MyDataSource2 myDataSource2 = applicationContext.getBean("myDataSource2", MyDataSource2.class);
        myDataSource2.getConnection();
        MyDataSource3 myDataSource3 = applicationContext.getBean("myDataSource3", MyDataSource3.class);
        myDataSource3.getConnection();
    }
}
