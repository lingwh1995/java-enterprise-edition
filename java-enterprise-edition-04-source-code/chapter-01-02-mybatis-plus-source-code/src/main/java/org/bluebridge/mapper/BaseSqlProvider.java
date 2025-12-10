package org.bluebridge.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.bluebridge.entity.QueryWrapper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author lingwh
 * @desc 通用Mapper SQL提供者
 * @date 2025/12/10 18:46
 */
public class BaseSqlProvider<T> {

    /**
     * 通用插入SQL生成
     */
    public String insert(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

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
                    // 忽略无法访问的字段
                }
            }
        }}.toString();
    }

    /**
     * 根据ID删除SQL生成
     */
    public String deleteById(Map<String, Object> params) {
        String tableName = (String) params.get("tableName");
        String idColumn = params.get("idColumn") != null ? params.get("idColumn").toString() : "id";
        Object idValue = params.get("id");

        if (tableName == null || idValue == null) {
            throw new IllegalArgumentException("Table name and ID value cannot be null");
        }

        return new SQL() {{
            DELETE_FROM(tableName);
            WHERE(idColumn + " = #{id}");
        }}.toString();
    }

    /**
     * 根据QueryWrapper删除SQL生成
     */
    public String delete(QueryWrapper<T> queryWrapper) {
        if (queryWrapper == null) {
            throw new IllegalArgumentException("QueryWrapper cannot be null");
        }

        // 获取实体类类型
        Class<T> entityClass = queryWrapper.getEntityClass();

        // 根据实体类类型获取表名
        String tableName = getTableName(entityClass);
        String conditionSql = queryWrapper.getConditionSql();

        return new SQL() {{
            DELETE_FROM(tableName);
            if (conditionSql != null && !conditionSql.trim().isEmpty()) {
                WHERE(conditionSql.substring(6)); // 去掉"WHERE "前缀
            }
        }}.toString();
    }

    /**
     * 更新SQL生成
     */
    public String update(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        Class<?> clazz = entity.getClass();
        String tableName = getTableName(clazz);

        return new SQL() {{
            UPDATE(tableName);

            Field[] fields = clazz.getDeclaredFields();
            boolean hasSetClause = false;
            // 添加SET子句
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(entity);
                    if (value != null && !isPrimaryKey(field)) {
                        SET(getColumnName(field) + " = #{" + field.getName() + "}");
                        hasSetClause = true;
                    }
                } catch (IllegalAccessException e) {
                    // 忽略无法访问的字段
                }
            }

            // 必须至少有一个SET子句
            if (!hasSetClause) {
                throw new IllegalArgumentException("Update operation requires at least one field to update");
            }

            // 添加WHERE条件
            boolean hasWhereClause = false;
            for (Field field : fields) {
                if (isPrimaryKey(field)) {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(entity);
                        if (value != null) {
                            WHERE(getColumnName(field) + " = #{" + field.getName() + "}");
                            hasWhereClause = true;
                        }
                    } catch (IllegalAccessException e) {
                        // 忽略无法访问的字段
                    }
                }
            }

            // 必须有WHERE条件
            if (!hasWhereClause) {
                throw new IllegalArgumentException("Update operation requires WHERE condition with primary key");
            }
        }}.toString();
    }

    /**
     * 根据ID查询SQL生成
     */
    public String selectById(Map<String, Object> params) {
        String tableName = (String) params.get("tableName");
        String idColumn = params.get("idColumn") != null ? params.get("idColumn").toString() : "id";
        Object idValue = params.get("id");

        if (tableName == null || idValue == null) {
            throw new IllegalArgumentException("Table name and ID value cannot be null");
        }

        return new SQL() {{
            SELECT("*");
            FROM(tableName);
            WHERE(idColumn + " = #{id}");
        }}.toString();
    }

    /**
     * 根据QueryWrapper查询单条记录SQL生成
     */
    public String select(QueryWrapper<T> queryWrapper) {
        if (queryWrapper == null) {
            throw new IllegalArgumentException("QueryWrapper cannot be null");
        }

        // 获取实体类类型
        Class<T> entityClass = queryWrapper.getEntityClass();

        // 根据实体类类型获取表名
        String tableName = getTableName(entityClass);
        String conditionSql = queryWrapper.getConditionSql();

        return new SQL() {{
            SELECT("*");
            FROM(tableName);
            if (conditionSql != null && !conditionSql.trim().isEmpty()) {
                WHERE(conditionSql.substring(6)); // 去掉"WHERE "前缀
            }
        }}.toString() + " LIMIT 1";
    }

    /**
     * 根据QueryWrapper查询列表SQL生成
     */
    public String selectList(QueryWrapper<T> queryWrapper) {
        if (queryWrapper == null) {
            throw new IllegalArgumentException("QueryWrapper cannot be null");
        }

        // 获取实体类类型
        Class<T> entityClass = queryWrapper.getEntityClass();

        // 根据实体类类型获取表名
        String tableName = getTableName(entityClass);
        String conditionSql = queryWrapper.getConditionSql();

        return new SQL() {{
            SELECT("*");
            FROM(tableName);
            if (conditionSql != null && !conditionSql.trim().isEmpty()) {
                WHERE(conditionSql.substring(6)); // 去掉"WHERE "前缀
            }
        }}.toString();
    }

    /**
     * 获取表名（根据类名转换规则）
     */
    private String getTableName(Class<?> clazz) {
        String className = clazz.getSimpleName();
        // 驼峰转下划线
        return camelToUnderline(className).toLowerCase();
    }

    /**
     * 获取列名（根据字段名转换规则）
     */
    private String getColumnName(Field field) {
        // 驼峰转下划线
        return camelToUnderline(field.getName()).toLowerCase();
    }

    /**
     * 驼峰命名转下划线
     */
    private String camelToUnderline(String camelCase) {
        if (camelCase == null || camelCase.isEmpty()) {
            return camelCase;
        }

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

    /**
     * 判断是否为主键字段
     * 可以扩展为通过注解判断
     */
    private boolean isPrimaryKey(Field field) {
        // 可以通过@Id等注解来判断主键
        return "id".equalsIgnoreCase(field.getName());
    }

}
