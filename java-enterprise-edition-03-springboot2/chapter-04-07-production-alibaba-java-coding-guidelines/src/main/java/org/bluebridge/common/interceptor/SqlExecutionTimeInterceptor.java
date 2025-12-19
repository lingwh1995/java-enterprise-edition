package org.bluebridge.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.ibatis.executor.Executor;

/**
 * @author lingwh
 * @desc SQL执行耗时统计拦截器
 * @date 2025/12/19 12:40
 */
@Slf4j(topic = "-")
@Intercepts({
        @Signature(type = Executor.class, method = "update",
                args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class,
                        ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class SqlExecutionTimeInterceptor implements Interceptor {

    /** 慢查询阈值（毫秒）*/
    private long longQueryTime = 1000;

    /** 是否显示SQL参数 */
    private boolean showParameters = true;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object parameterObject = args[1];

        // 获取SQL信息
        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
        String sqlId = mappedStatement.getId();
        String sql = boundSql.getSql().replaceAll("\\s+", " ");

        long start = System.currentTimeMillis();
        try {
            // 执行目标方法
            return invocation.proceed();
        } finally {
            long executionTime = System.currentTimeMillis() - start;
            showSqlExecutionTime(sqlId, sql, parameterObject, executionTime);
        }
    }

    /**
     * 显示SQL执行耗时
     * @param sqlId
     * @param sql
     * @param params
     * @param executionTime
     */
    private void showSqlExecutionTime(String sqlId, String sql,
                                   Object params, long executionTime) {
        executorService.submit(() -> {
            // 基础日志
            String sqlExecutionLog = String.format("SQL执行耗时: %dms | %s - ==> %s",
                    executionTime, sqlId, sql);

            // 参数日志（敏感数据需脱敏）
            if (showParameters && params != null) {
                String paramStr = params.toString();
                // 简单脱敏处理（实际项目应使用专业脱敏工具）
                paramStr = paramStr.replaceAll("(\"password\":\")([^\"]+)(\")",
                        "$1****$3");
                sqlExecutionLog += " | 参数: " + paramStr;
            }

            // 分级日志输出
            if (executionTime > longQueryTime) {
                log.warn("[慢查询告警] {}", sqlExecutionLog);
            }else {
                log.debug("{}", sqlExecutionLog);
            }
        });
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 从配置加载参数
        // 获取用户自定义的慢查询阈值
        String longQueryTimeUserDefined = properties.getProperty("longQueryTime");
        if (longQueryTimeUserDefined != null) {
            this.longQueryTime = Long.parseLong(longQueryTimeUserDefined);
        }

        String showParams = properties.getProperty("showParameters");
        if (showParams != null) {
            this.showParameters = Boolean.parseBoolean(showParams);
        }
    }

}