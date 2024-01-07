package org.bluebridge.reflect.object;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SpringTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringTest.class);

    /**
     * 测试方法调用的四个要素：
     *      1.调用的是哪个对象
     *      2.调用的是哪个方法
     *      3.调用该方法需要什么参数
     *      4.方法执行完成会返回什么返回值
     */
    @Test
    public void testNormalInvokeMethod() {
        Cat cat = new Cat();
        cat.drink();
        String catName = cat.showCatInfos("001","煤球");
        logger.info(catName);
        String catNameAndAge = cat.showCatInfos("001","煤球", 1);
        logger.info(catNameAndAge);
    }

    /**
     * 测试反射调用方法
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Test
    public void testReflectInvokeMethod() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        //1.获取对象
        Class<?> catClazz = Class.forName("org.bluebridge.reflect.object.Cat");
        Constructor<?> constructor = catClazz.getDeclaredConstructor();
        Cat cat = (Cat) constructor.newInstance();
        //2.获取方法
        Method showCatInfos = catClazz.getDeclaredMethod("showCatInfos", String.class, String.class, Integer.class);
        //3.调用方法    4.获取方法返回值
        Object catNameAndAge = showCatInfos.invoke(cat,"002", "煤球",2);
        logger.info(catNameAndAge.toString());
    }

    /**
     * 测试反射获取属性信息
     */
    @Test
    public void testReflectGetPropertyInfo() throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        //1.获取对象
        Class<?> catClazz = Class.forName("org.bluebridge.reflect.object.Cat");
        //2.获取属性信息
        Field nameProperty = catClazz.getDeclaredField("name");
        //获取属性类型
        Class<?> type = nameProperty.getType();
        logger.info(type.toString());
        //获取属性类型简单名称
        String simpleName = nameProperty.getType().getSimpleName();
        logger.info(simpleName);
    }
}
