package org.bluebridge.resource.auto_choice_resource;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class SpringTest {

    /**
     * 测试在IoC容器中根据配置的Resouce类型的属性的value的值的前缀自动注入一个Resource接口的实现类
     */
    @Test
    public void testAutoChoiceResourceBySpringIoC() throws IOException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-auto_choice_resource.xml");
        //让Spring容器根据
        AutoChoiceResourceBySpringIoC autoChoiceResourceBySpringIoC = applicationContext.getBean("autoChoiceResourceBySpringIoC", AutoChoiceResourceBySpringIoC.class);
        autoChoiceResourceBySpringIoC.parseResource();
    }

}
