package org.bluebridge.anno;

import org.bluebridge.enums.IdType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主键注解，用于指定实体类的主键字段和主键生成策略
 *
 * @author lingwh
 * @date 2025/12/11 18:35
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableId {

    String value() default "id";

    IdType type() default IdType.NONE;

}
