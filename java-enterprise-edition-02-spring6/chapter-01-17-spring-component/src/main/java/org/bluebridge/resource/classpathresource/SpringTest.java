package org.bluebridge.resource.classpathresource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class SpringTest {

    /**
     *  测试访问类路径资源
     * @throws IOException
     */
    @Test
    public void testLoadAndReadClassPathResource() throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-resource.xml");
        ClassPathResourceDemo classPathResourceDemo = applicationContext.getBean("classPathResourceDemo", ClassPathResourceDemo.class);
        String path = "applicationContext-resource.xml";
        classPathResourceDemo.loadAndParseClassPathResource(path);
    }

}
