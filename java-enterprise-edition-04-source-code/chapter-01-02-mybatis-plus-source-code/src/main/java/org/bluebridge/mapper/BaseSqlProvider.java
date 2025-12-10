package org.bluebridge.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author lingwh
 * @desc 通用Mapper SQL提供者
 * @date 2025/12/10 18:46
 */
public class BaseSqlProvider {

    /**
     * 通用插入SQL生成
     */
    public String insert(Object entity) {
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

        return new SQL() {{
            DELETE_FROM(tableName);
            WHERE(idColumn + " = #{id}");
        }}.toString();
    }

    /**
     * 根据实体删除SQL生成
     */
    public String delete(Object entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        Class<?> clazz = entity.getClass();
        String tableName = getTableName(clazz);

        return new SQL() {{
            DELETE_FROM(tableName);

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(entity);
                    if (value != null) {
                        WHERE(getColumnName(field) + " = #{" + field.getName() + "}");
                    }
                } catch (IllegalAccessException e) {
                    // 忽略无法访问的字段
                }
            }
        }}.toString();
    }

    /**
     * 更新SQL生成
     */
    public String update(Object entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        Class<?> clazz = entity.getClass();
        String tableName = getTableName(clazz);

        return new SQL() {{
            UPDATE(tableName);

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(entity);
                    if (value != null && !isPrimaryKey(field)) {
                        SET(getColumnName(field) + " = #{" + field.getName() + "}");
                    }
                } catch (IllegalAccessException e) {
                    // 忽略无法访问的字段
                }
            }

            // 添加WHERE条件
            for (Field field : fields) {
                if (isPrimaryKey(field)) {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(entity);
                        if (value != null) {
                            WHERE(getColumnName(field) + " = #{" + field.getName() + "}");
                        }
                    } catch (IllegalAccessException e) {
                        // 忽略无法访问的字段
                    }
                }
            }
        }}.toString();
    }

    /**
     * 根据ID查询SQL生成
     */
    public String selectById(Map<String, Object> params) {
        String tableName = (String) params.get("tableName");
        String idColumn = params.get("idColumn") != null ? params.get("idColumn").toString() : "id";

        return new SQL() {{
            SELECT("*");
            FROM(tableName);
            WHERE(idColumn + " = #{id}");
        }}.toString();
    }

    /**
     * 根据实体查询单条记录SQL生成
     */
    public String select(Object entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        Class<?> clazz = entity.getClass();
        String tableName = getTableName(clazz);

        return new SQL() {{
            SELECT("*");
            FROM(tableName);

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(entity);
                    if (value != null) {
                        WHERE(getColumnName(field) + " = #{" + field.getName() + "}");
                    }
                } catch (IllegalAccessException e) {
                    // 忽略无法访问的字段
                }
            }

            // 添加LIMIT 1限制
            // 注意：LIMIT在MyBatis的SQL类中需要特殊处理
        }}.toString() + " LIMIT 1";
    }

    /**
     * 根据实体查询列表SQL生成
     */
    public String selectList(Object entity) {
        if (entity == null) {
            // 如果实体为空，查询所有记录
            Class<?> clazz = entity.getClass();
            String tableName = getTableName(clazz);

            return new SQL() {{
                SELECT("*");
                FROM(tableName);
            }}.toString();
        }

        Class<?> clazz = entity.getClass();
        String tableName = getTableName(clazz);

        return new SQL() {{
            SELECT("*");
            FROM(tableName);

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(entity);
                    if (value != null) {
                        WHERE(getColumnName(field) + " = #{" + field.getName() + "}");
                    }
                } catch (IllegalAccessException e) {
                    // 忽略无法访问的字段
                }
            }
        }}.toString();
    }

    /**
     * 获取表名（可以根据类名转换规则自定义）
     */
    private String getTableName(Class<?> clazz) {
        // 可以根据具体需求定制表名生成规则
        // 例如：User -> user, UserInfo -> user_info
        return "table_name"; // 默认值，实际使用时需要替换
    }

    /**
     * 获取列名（可以根据字段名转换规则自定义）
     */
    private String getColumnName(Field field) {
        // 可以根据具体需求定制列名生成规则
        // 例如：userName -> user_name, firstName -> first_name
        return field.getName(); // 默认使用字段名
    }

    /**
     * 判断是否为主键字段
     */
    private boolean isPrimaryKey(Field field) {
        // 可以通过注解等方式判断主键
        // 例如：@Id 注解标记的字段
        return "id".equals(field.getName());
    }
}
