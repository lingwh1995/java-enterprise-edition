package org.bluebridge.mapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;
import org.bluebridge.anno.TableField;
import org.bluebridge.anno.TableName;
import org.bluebridge.wrapper.Wrapper;

import java.lang.reflect.Field;

/**
 * @author lingwh
 * @desc 通用Mapper SQL提供者
 * @date 2025/12/10 18:46
 */
@Slf4j
public class BaseSqlProvider {

    public String insert(Object entity) {
        Class<?> clazz = entity.getClass();
        String tableName = getTableName(clazz);

        return new SQL() {{
            INSERT_INTO(tableName);
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(entity);
                    if (value != null) {
                        VALUES(getColumnName(field), "#{" + field.getName() + "}");
                    }
                } catch (IllegalAccessException e) {
                    // ignore
                }
            }
        }}.toString();
    }

    public String deleteById(Class<?> clazz, Object id) {
        String tableName = getTableName(clazz);
        return new SQL() {{
            DELETE_FROM(tableName);
            WHERE("id = #{id}");
        }}.toString();
    }

    public String updateById(Object entity) {
        Class<?> clazz = entity.getClass();
        String tableName = getTableName(clazz);

        return new SQL() {{
            UPDATE(tableName);
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(entity);
                    if (value != null && !"id".equals(field.getName())) {
                        SET(getColumnName(field) + " = #{" + field.getName() + "}");
                    }
                } catch (IllegalAccessException e) {
                    // ignore
                }
            }
            WHERE("id = #{id}");
        }}.toString();
    }

    public String selectById(Class<?> clazz, Object id) {
        String tableName = getTableName(clazz);
        return new SQL() {{
            SELECT("*");
            FROM(tableName);
            WHERE("id = #{id}");
        }}.toString();
    }

    public String selectList(Wrapper<?> wrapper) {
        Class<?> clazz = wrapper.getEntityClass();
        String tableName = getTableName(clazz);
        String condition = wrapper.getSqlSegment();

        SQL sql = new SQL() {{
            SELECT("*");
            FROM(tableName);
        }};

        if (!condition.isEmpty()) {
            // 移除开头的" WHERE "
            if (condition.startsWith(" WHERE ")) {
                condition = condition.substring(7);
            }
            sql.WHERE(condition);
        }

        return sql.toString();
    }

    /**
     * 获取表名（根据类名转换规则）
     */
    private String getTableName(Class<?> clazz) {
        TableName tableAnnotation = clazz.getAnnotation(TableName.class);
        if (tableAnnotation != null && !tableAnnotation.value().isEmpty()) {
            return tableAnnotation.value();
        }
        return camelToUnderline(clazz.getSimpleName());
    }

    /**
     * 获取列名（根据字段名转换规则）
     */
    private String getColumnName(Field field) {
        TableField fieldAnnotation = field.getAnnotation(TableField.class);
        if (fieldAnnotation != null && !fieldAnnotation.value().isEmpty()) {
            return fieldAnnotation.value();
        }
        return camelToUnderline(field.getName());
    }

    /**
     * 驼峰命名转下划线
     */
    private String camelToUnderline(String camelCase) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < camelCase.length(); i++) {
            char c = camelCase.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    result.append("_");
                }
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

}