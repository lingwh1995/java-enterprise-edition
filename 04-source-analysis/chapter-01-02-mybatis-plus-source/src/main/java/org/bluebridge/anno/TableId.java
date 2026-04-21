package org.bluebridge.anno;

import org.bluebridge.enums.IdType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/11 18:35
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableId {
    String value() default "id";
    IdType type() default IdType.NONE;
}
