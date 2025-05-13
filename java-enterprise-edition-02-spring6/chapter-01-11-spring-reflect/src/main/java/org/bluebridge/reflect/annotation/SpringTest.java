package org.bluebridge.reflect.annotation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;


public class SpringTest {

    private static final Logger logger = LogManager.getLogger(SpringTest.class);

    /**
     * 测试通过反射获取注解信息
     * @throws ClassNotFoundException
     */
    @Test
    public void testReflectAnnotation() throws ClassNotFoundException {
        Class<?> aClass = Class.forName("org.bluebridge.reflect.annotation.User");
        if(aClass.isAnnotationPresent(Component.class)) {
            Component annotation = aClass.getAnnotation(Component.class);
            String annotationValue = annotation.value();
            logger.info(annotationValue);
        }
    }
}
