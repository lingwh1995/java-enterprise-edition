package org.bluebridge.common.interceptor;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.lang.reflect.Field;

/**
 * MyBatis逻辑删除拦截器
 * 符合阿里巴巴Java开发手册要求，在DAO层统一处理逻辑删除过滤
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
@Component
public class SoftDeleteInterceptor implements Interceptor {

    // 逻辑删除字段配置
    private String softDeleteColumn = "is_deleted";
    private String notDeletedValue = "0";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];

        // 只处理SELECT语句
        if (ms.getSqlCommandType().name().equals("SELECT")) {
            // 获取原始SQL
            BoundSql boundSql = getBoundSql(invocation);
            String originalSql = boundSql.getSql().trim();

            // 检查是否需要注入逻辑删除条件
            if (isInjectSoftDelete(originalSql)) {
                // 注入逻辑删除条件
                String newSql = injectSoftDeleteCondition(originalSql);

                // 更新BoundSql中的SQL
                updateBoundSql(boundSql, newSql);
            }
        }

        return invocation.proceed();
    }

    /**
     * 获取BoundSql对象
     */
    private BoundSql getBoundSql(Invocation invocation) {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];

        if (args.length >= 6) {
            // 如果是6参数的方法调用，直接返回第6个参数
            return (BoundSql) args[5];
        } else {
            // 否则通过MappedStatement获取
            return ms.getBoundSql(parameter);
        }
    }

    /**
     * 判断是否需要注入逻辑删除条件
     */
    private boolean isInjectSoftDelete(String sql) {
        // 转为大写进行比较
        String upperSql = sql.toUpperCase();

        // 如果SQL中已经包含了逻辑删除字段，则不再注入
        return !upperSql.contains(softDeleteColumn.toUpperCase());
    }

    /**
     * 注入逻辑删除条件
     */
    private String injectSoftDeleteCondition(String originalSql) {
        String upperSql = originalSql.toUpperCase();

        // 查找WHERE子句的位置
        int whereIndex = upperSql.indexOf(" WHERE ");

        if (whereIndex != -1) {
            // 如果已有WHERE子句，在其后添加AND条件
            return originalSql + " AND " + softDeleteColumn + " = " + notDeletedValue;
        } else {
            // 如果没有WHERE子句，查找合适的位置插入WHERE条件
            return injectWhereClause(originalSql);
        }
    }

    /**
     * 在合适位置插入WHERE子句
     */
    private String injectWhereClause(String originalSql) {
        String upperSql = originalSql.toUpperCase();

        // 查找常见的SQL关键字位置
        int groupByIndex = upperSql.indexOf(" GROUP BY ");
        int orderByIndex = upperSql.indexOf(" ORDER BY ");
        int limitIndex = upperSql.indexOf(" LIMIT ");

        // 确定插入位置
        int insertIndex = originalSql.length(); // 默认在末尾

        if (groupByIndex != -1 && groupByIndex < insertIndex) {
            insertIndex = groupByIndex;
        }
        if (orderByIndex != -1 && orderByIndex < insertIndex) {
            insertIndex = orderByIndex;
        }
        if (limitIndex != -1 && limitIndex < insertIndex) {
            insertIndex = limitIndex;
        }

        // 在确定的位置插入WHERE子句
        return originalSql.substring(0, insertIndex) +
                " WHERE " + softDeleteColumn + " = " + notDeletedValue +
                originalSql.substring(insertIndex);
    }

    /**
     * 更新BoundSql中的SQL语句
     */
    private void updateBoundSql(BoundSql boundSql, String newSql) throws NoSuchFieldException, IllegalAccessException {
        // 通过反射修改BoundSql中的SQL
        Field sqlField = BoundSql.class.getDeclaredField("sql");
        sqlField.setAccessible(true);
        sqlField.set(boundSql, newSql);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String column = properties.getProperty("logicDeleteColumn");
        if (column != null && !column.isEmpty()) {
            this.softDeleteColumn = column;
        }

        String value = properties.getProperty("notDeletedValue");
        if (value != null && !value.isEmpty()) {
            this.notDeletedValue = value;
        }
    }
}