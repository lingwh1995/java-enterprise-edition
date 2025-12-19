package org.bluebridge.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @author lingwh
 * @desc MyBatis逻辑删除拦截器
 * @date 2025/12/19 11:23
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class SoftDeleteInterceptor implements Interceptor {

    /**
     * 逻辑删除字段名
     */
    private static final String DELETE_FIELD = "is_deleted";
    
    /**
     * 未删除状态值
     */
    private static final int NOT_DELETED_VALUE = 0;
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object parameter = args[1];
        
        // 获取原始SQL
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        String originalSql = boundSql.getSql().trim();
        
        // 处理SELECT语句，添加逻辑删除过滤条件
        String processedSql = processSelectSql(originalSql);
        
        if (!originalSql.equals(processedSql)) {
            // 如果SQL发生变化，则创建新的MappedStatement
            MappedStatement newMappedStatement = copyMappedStatement(mappedStatement, processedSql, boundSql);
            args[0] = newMappedStatement;
        }
        
        return invocation.proceed();
    }

    /**
     * 处理SELECT语句，添加逻辑删除过滤条件
     *
     * @param originalSql 原始SQL
     * @return 处理后的SQL
     */
    private String processSelectSql(String originalSql) {
        // 只处理SELECT语句
        if (!originalSql.toUpperCase().startsWith("SELECT")) {
            return originalSql;
        }
        
        // 检查是否已经包含了逻辑删除条件（更严格的检查）
        String lowerSql = originalSql.toLowerCase();
        if (lowerSql.contains(DELETE_FIELD)) {
            // 如果已经明确处理了逻辑删除，则不再添加
            log.debug("SQL已包含逻辑删除条件，无需重复添加: {}", originalSql);
            return originalSql;
        }
        
        // 添加逻辑删除过滤条件
        String processedSql = addLogicDeleteFilter(originalSql);
        log.debug("逻辑删除拦截器处理SQL: {} -> {}", originalSql, processedSql);
        
        return processedSql;
    }

    /**
     * 为SQL添加逻辑删除过滤条件
     *
     * @param sql 原始SQL
     * @return 添加逻辑删除条件后的SQL
     */
    private String addLogicDeleteFilter(String sql) {
        // 统一转换为大写进行比较，但保留原始大小写用于返回
        String upperSql = sql.toUpperCase();
        
        // 查找WHERE子句的位置
        int whereIndex = upperSql.lastIndexOf(" WHERE ");
        
        if (whereIndex != -1) {
            // 如果已有WHERE子句，在末尾添加AND条件
            // 确保在正确的括号内添加条件
            int orderByIndex = upperSql.lastIndexOf(" ORDER BY ");
            int limitIndex = upperSql.lastIndexOf(" LIMIT ");
            int groupByIndex = upperSql.lastIndexOf(" GROUP BY ");
            
            // 找到WHERE子句结束位置
            int endIndex = sql.length();
            if (orderByIndex != -1 && orderByIndex > whereIndex) {
                endIndex = orderByIndex;
            } else if (limitIndex != -1 && limitIndex > whereIndex) {
                endIndex = limitIndex;
            } else if (groupByIndex != -1 && groupByIndex > whereIndex) {
                endIndex = groupByIndex;
            }
            
            // 检查WHERE子句是否已经有逻辑删除相关的条件
            String whereClause = sql.substring(whereIndex, endIndex).toLowerCase();
            if (whereClause.contains(DELETE_FIELD.toLowerCase())) {
                log.debug("WHERE子句已包含逻辑删除字段，无需重复添加: {}", whereClause);
                return sql; // 已经有相关条件，不需要再添加
            }
            
            return sql.substring(0, endIndex) + " AND " + DELETE_FIELD + " = " + NOT_DELETED_VALUE +
                    sql.substring(endIndex);
        } else {
            // 如果没有WHERE子句，添加WHERE条件
            // 查找ORDER BY或LIMIT的位置
            int orderByIndex = upperSql.lastIndexOf(" ORDER BY ");
            int limitIndex = upperSql.lastIndexOf(" LIMIT ");
            int groupByIndex = upperSql.lastIndexOf(" GROUP BY ");
            
            int insertPosition = sql.length();
            if (orderByIndex != -1) {
                insertPosition = orderByIndex;
            } else if (limitIndex != -1) {
                insertPosition = limitIndex;
            } else if (groupByIndex != -1) {
                insertPosition = groupByIndex;
            }
            
            return sql.substring(0, insertPosition) + " WHERE " + DELETE_FIELD + " = " + NOT_DELETED_VALUE +
                    sql.substring(insertPosition);
        }
    }
    
    /**
     * 复制MappedStatement并替换SQL
     *
     * @param mappedStatement 原始MappedStatement
     * @param newSql 新的SQL
     * @param boundSql BoundSql对象
     * @return 新的MappedStatement
     * @throws Exception 反射异常
     */
    private MappedStatement copyMappedStatement(MappedStatement mappedStatement, String newSql, BoundSql boundSql) throws Exception {
        // 使用反射创建新的BoundSql
        BoundSql newBoundSql = new BoundSql(
                mappedStatement.getConfiguration(),
                newSql,
                boundSql.getParameterMappings(),
                boundSql.getParameterObject()
        );
        
        // 复制原始BoundSql中的附加参数
        Field metaParamsField = BoundSql.class.getDeclaredField("metaParameters");
        metaParamsField.setAccessible(true);
        metaParamsField.set(newBoundSql, metaParamsField.get(boundSql));
        
        Field additionalParamsField = BoundSql.class.getDeclaredField("additionalParameters");
        additionalParamsField.setAccessible(true);
        additionalParamsField.set(newBoundSql, additionalParamsField.get(boundSql));
        
        // 创建新的SqlSource
        SqlSource sqlSource = new SqlSource() {
            @Override
            public BoundSql getBoundSql(Object parameterObject) {
                return newBoundSql;
            }
        };
        
        // 构建新的MappedStatement
        return new MappedStatement.Builder(
                mappedStatement.getConfiguration(),
                mappedStatement.getId(),
                sqlSource,
                mappedStatement.getSqlCommandType()
        )
                .resource(mappedStatement.getResource())
                .fetchSize(mappedStatement.getFetchSize())
                .timeout(mappedStatement.getTimeout())
                .statementType(mappedStatement.getStatementType())
                .keyGenerator(mappedStatement.getKeyGenerator())
                .keyProperty(arrayToString(mappedStatement.getKeyProperties()))
                .keyColumn(arrayToString(mappedStatement.getKeyColumns()))
                .databaseId(mappedStatement.getDatabaseId())
                .lang(mappedStatement.getLang())
                .resultMaps(mappedStatement.getResultMaps())
                .resultSetType(mappedStatement.getResultSetType())
                .flushCacheRequired(mappedStatement.isFlushCacheRequired())
                .useCache(mappedStatement.isUseCache())
                .resultOrdered(mappedStatement.isResultOrdered())
                .build();
    }
    
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
    
    @Override
    public void setProperties(Properties properties) {
        // 可以在这里设置自定义属性
    }
    
    /**
     * 将字符串数组转换为逗号分隔的字符串
     *
     * @param array 字符串数组
     * @return 逗号分隔的字符串
     */
    private String arrayToString(String[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }
}