package org.bluebridge.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表名注解，用于指定实体类对应的数据库表名
 *
 * @author lingwh
 * @date 2025/12/11 18:34
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableName {

    String value() default "";

}
