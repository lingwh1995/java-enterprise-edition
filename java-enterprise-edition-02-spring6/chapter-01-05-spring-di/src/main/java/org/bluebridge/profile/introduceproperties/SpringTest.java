package org.bluebridge.profile.introduceproperties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;


public class SpringTest {

    private static final Logger logger = LogManager.getLogger(SpringTest.class);

    /**
     * 测试使用 context:property-placeholder 引入外部的properties文件
     * key的命名是不规范的，无法获取到properties文件中正确的username属性的值
     */
    @Test
    public void testIntroducePropertiesProfileKeyInvalid() throws SQLException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/introduceproperties/key-invalid/applicationContext-introduce-propertiesl.xml");
        MyDataSource4 myDataSource4KeyInvalid = applicationContext.getBean("myDataSource4KeyInvalid", MyDataSource4.class);
        myDataSource4KeyInvalid.getConnection();
    }

    /**
     * 测试使用 context:property-placeholder 引入外部的properties文件
     * key的命名是标准的，可以获取到properties文件中正确的jdbc.username属性的值
     */
    @Test
    public void testIntroducePropertiesProfileKeyStandard() throws SQLException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/introduceproperties/key-standrad/applicationContext-introduce-propertiesl.xml");
        MyDataSource5 myDataSource5KeyStandard = applicationContext.getBean("myDataSource5KeyStandard", MyDataSource5.class);
        myDataSource5KeyStandard.getConnection();
    }

}
