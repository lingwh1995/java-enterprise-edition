package org.bluebridge.reflect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 元注解：用来修饰注解可以出现的位置
 *      ElementType.TYPE     可以出现在类上
 *      ElementType.FIELD    可以出现在属性上
 */
@Target(value = {ElementType.TYPE, ElementType.FIELD})
/**
 * 用来标注注解可以出现在什么时候
 *      RetentionPolicy.RUNTIME     用来标注@Component最终保留在class文件中，可以被反射机制读取
 *      RetentionPolicy.SOURCE      用来标注@Component只能出现在源文件中
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {

    /**
     * 定义注解的属性
     * String是属性类型
     * value是属性名称
     * 特别注意：如果属性名是value，则使用时value可以省略
     * @return
     */
    String value();
}