package org.bluebridge.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * bean工具类测试
 */
@Slf4j
public class BeanUtilsTest {

    /**
     * 测试拷贝对象属性
     */
    @Test
    public void testCopyProperties() {
        User user1 = new User(1l, "张三", 18);
        User user2 = new User();
        BeanUtils.copyProperties(user1, user2);
        log.debug("user2: {}", user2);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", 2l);
        map1.put("name", "李四");
        map1.put("age", 28);
        Map<String, String> map2 = new HashMap<>();
        BeanUtils.copyProperties(map1, map2, Map.class);
        log.debug("map2: {}", map2);
    }

    /**
     * 测试实例化某个对象
     */
    @Test
    public void testInstantiateClass() {
        User user = BeanUtils.instantiateClass(User.class);
        log.debug("user: {}", user);
    }

    /**
     *测试获取指定类的指定方法
     */
    @Test
    public void testFindDeclaredMethod() {
        Method declaredMethod = BeanUtils.findDeclaredMethod(User.class, "getId");
        log.debug("declaredMethod: {}", declaredMethod.getName());
    }

    /**
     * 测试获取指定方法的参数
     */
    @Test
    public void testFindPropertyForMethod() {
        Method declaredMethod = BeanUtils.findDeclaredMethod(User.class, "getId");
        PropertyDescriptor propertyForMethod = BeanUtils.findPropertyForMethod(declaredMethod);
        log.debug("propertyForMethod: {}", propertyForMethod.getName());
    }

    /**
     * 测试获取方法
     */
    @Test
    public void testFindMethod() {
        Method method = ReflectionUtils.findMethod(User.class, "getId");
        log.debug("method: {}", method);
    }

    /**
     * 测试获取字段
     */
    @Test
    public void testFindField() {
        Field field = ReflectionUtils.findField(User.class, "id");
        log.debug("field: {}", field);
        // 测试字段是否是常量
        log.debug("字段是否是常量: {}", ReflectionUtils.isPublicStaticFinal(field));
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class User {
    private Long id;
    private String name;
    private Integer age;
}