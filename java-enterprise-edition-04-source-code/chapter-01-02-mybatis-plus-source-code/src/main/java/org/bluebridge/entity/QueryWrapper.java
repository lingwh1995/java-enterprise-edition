package org.bluebridge.entity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/10 22:37
 */
public class QueryWrapper<T> {

    private List<String> conditions;
    private List<Object> params;
    private Class<T> entityClass;

    public QueryWrapper() {
        this.conditions = new ArrayList<>();
        this.params = new ArrayList<>();
        this.entityClass = getGenericClass();
    }

    public QueryWrapper(Class<T> entityClass) {
        this.conditions = new ArrayList<>();
        this.params = new ArrayList<>();
        this.entityClass = entityClass;
    }

    /**
     * 获取实体类的Class对象
     * @return 实体类的Class对象
     */
    public Class<T> getEntityClass() {
        return entityClass;
    }

    /**
     * 通过反射获取泛型类型
     * @return 泛型类型的Class对象
     */
    public Class<T> getGenericClass() {
// 获取泛型变量
        TypeVariable<? extends Class<? extends QueryWrapper>>[] typeParameters = getClass().getTypeParameters();
        System.out.println(typeParameters[0].getName()); // 输出：T
        return (Class<T>) typeParameters[0].getBounds()[0];
    }

    /**
     * 等于条件
     * @param column 字段名
     * @param value 值
     * @return QueryWrapper
     */
    public QueryWrapper<T> eq(String column, Object value) {
        conditions.add(column + " = ?");
        params.add(value);
        return this;
    }

    /**
     * 不等于条件
     * @param column 字段名
     * @param value 值
     * @return QueryWrapper
     */
    public QueryWrapper<T> ne(String column, Object value) {
        conditions.add(column + " != ?");
        params.add(value);
        return this;
    }

    /**
     * 大于条件
     * @param column 字段名
     * @param value 值
     * @return QueryWrapper
     */
    public QueryWrapper<T> gt(String column, Object value) {
        conditions.add(column + " > ?");
        params.add(value);
        return this;
    }

    /**
     * 大于等于条件
     * @param column 字段名
     * @param value 值
     * @return QueryWrapper
     */
    public QueryWrapper<T> ge(String column, Object value) {
        conditions.add(column + " >= ?");
        params.add(value);
        return this;
    }

    /**
     * 小于条件
     * @param column 字段名
     * @param value 值
     * @return QueryWrapper
     */
    public QueryWrapper<T> lt(String column, Object value) {
        conditions.add(column + " < ?");
        params.add(value);
        return this;
    }

    /**
     * 小于等于条件
     * @param column 字段名
     * @param value 值
     * @return QueryWrapper
     */
    public QueryWrapper<T> le(String column, Object value) {
        conditions.add(column + " <= ?");
        params.add(value);
        return this;
    }

    /**
     * LIKE条件
     * @param column 字段名
     * @param value 值
     * @return QueryWrapper
     */
    public QueryWrapper<T> like(String column, Object value) {
        conditions.add(column + " LIKE ?");
        params.add("%" + value + "%");
        return this;
    }

    /**
     * IN条件
     * @param column 字段名
     * @param values 值列表
     * @return QueryWrapper
     */
    public QueryWrapper<T> in(String column, List<Object> values) {
        StringBuilder sb = new StringBuilder();
        sb.append(column).append(" IN (");
        for (int i = 0; i < values.size(); i++) {
            sb.append("?");
            if (i < values.size() - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        conditions.add(sb.toString());
        params.addAll(values);
        return this;
    }

    /**
     * 排序 ASC
     * @param column 字段名
     * @return QueryWrapper
     */
    public QueryWrapper<T> orderByAsc(String column) {
        conditions.add("ORDER BY " + column + " ASC");
        return this;
    }

    /**
     * 排序 DESC
     * @param column 字段名
     * @return QueryWrapper
     */
    public QueryWrapper<T> orderByDesc(String column) {
        conditions.add("ORDER BY " + column + " DESC");
        return this;
    }

    /**
     * 获取SQL条件部分
     * @return 条件字符串
     */
    public String getConditionSql() {
        if (conditions.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" WHERE ");
        for (int i = 0; i < conditions.size(); i++) {
            if (i > 0 && !conditions.get(i).startsWith("ORDER BY")) {
                sb.append(" AND ");
            }
            sb.append(conditions.get(i));
        }
        return sb.toString();
    }

    /**
     * 获取参数列表
     * @return 参数列表
     */
    public List<Object> getParams() {
        return params;
    }

}