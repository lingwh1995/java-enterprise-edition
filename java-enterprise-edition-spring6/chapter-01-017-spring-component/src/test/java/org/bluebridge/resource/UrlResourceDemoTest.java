package org.bluebridge.resource;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;


public class UrlResourceDemoTest {

    /**
     *  测试访问网络资源
      * @throws IOException
     */
    @Test
    public void testLoadAndReadUrlResource() throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-resource.xml");
        UrlResourceDemo urlResourceDemo = applicationContext.getBean("urlResourceDemo",UrlResourceDemo.class);
        //String path = "file:pom.xml";
        String path = "https://www.baidu.com";
        urlResourceDemo.loadAndParseUrlResource(path);
    }

}
