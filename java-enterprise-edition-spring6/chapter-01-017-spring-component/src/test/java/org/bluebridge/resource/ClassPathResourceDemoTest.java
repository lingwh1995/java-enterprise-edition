package org.bluebridge.resource;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class ClassPathResourceDemoTest {

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
