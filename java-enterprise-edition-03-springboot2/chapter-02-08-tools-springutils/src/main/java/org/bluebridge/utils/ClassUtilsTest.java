package org.bluebridge.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.ClassUtils;


/**
 * Class工具类测试
 */
@Slf4j
public class ClassUtilsTest {

    /**
     * 测试Class工具类
     * @throws Exception
     */
    @Test
    public void testClassUtils() throws Exception {
        // 获取对象的所有接口
        Class<?>[] allInterfaces = ClassUtils.getAllInterfaces(ClassUtilsTest.class);
        log.debug("allInterfaces: {}", allInterfaces);

        // 获取某个类的所在的包名
        String packageName = ClassUtils.getPackageName(ClassUtilsTest.class);
        log.debug("packageName: {}", packageName);

        // 判断某个类是否内部类
        boolean innerClass = ClassUtils.isInnerClass(ClassUtilsTest.class);
        log.debug("innerClass: {}", innerClass);

        // 判断对象是否代理对象
        boolean cglibProxy = ClassUtils.isCglibProxy(new ClassUtilsTest());
        log.debug("cglibProxy: {}", cglibProxy);
    }

}
