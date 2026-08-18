package org.bluebridge.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段注解，用于指定实体属性对应的数据库表字段名
 *
 * @author lingwh
 * @date 2025/12/11 18:34
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableField {

    String value() default "";

}
