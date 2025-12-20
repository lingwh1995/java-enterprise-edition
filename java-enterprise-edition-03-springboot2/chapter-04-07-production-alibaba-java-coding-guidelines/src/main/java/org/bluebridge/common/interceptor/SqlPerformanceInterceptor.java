package org.bluebridge.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.ibatis.executor.Executor;
import org.bluebridge.common.dto.QueryDTO;
import org.bluebridge.common.util.SqlFormatterUtils;

/**
 * @author lingwh
 * @desc SQL执行耗时统计+格式化拦截器
 * @date 2025/12/19 12:40
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "update",
                args = {MappedStatement.class, Object.class}),
        @Signature(type = StatementHandler.class, method = "parameterize",
                args = {Statement.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class,
                        ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class SqlPerformanceInterceptor implements Interceptor {

    /** 慢查询阈值（毫秒）*/
    private long longQueryTime = 1000;

    // 是否打印原始SQL，默认为true
    private boolean showOriginalSql = true;

    /** 是否显示格式化后的SQL */
    private boolean showFormattedSql = true;

    /** 线程池，用于异步日志记录 */
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取拦截的方法
        String methodName = invocation.getMethod().getName();
        Object target = invocation.getTarget();

        // 如果是StatementHandler的parameterize方法，直接放行
        if ("parameterize".equals(methodName) && target instanceof StatementHandler) {
            return invocation.proceed();
        }

        Object[] args = invocation.getArgs();

        // 确保第一个参数是MappedStatement
        if (args.length > 0 && args[0] instanceof MappedStatement) {
            MappedStatement mappedStatement = (MappedStatement) args[0];
            Object parameterObject = null;
            if (args.length > 1) {
                parameterObject = args[1];
            }

            // 获取SQL信息
            BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
            String sqlId = mappedStatement.getId();
            Configuration configuration = mappedStatement.getConfiguration();
            long start = System.currentTimeMillis();
            // 执行目标方法
            Object invocationResult = invocation.proceed();
            long executionTime = System.currentTimeMillis() - start;
            showSqlExecutionLog(sqlId, boundSql, configuration, executionTime, invocationResult);
            return invocationResult;
        } else {
            // 不是我们关心的拦截点，直接放行
            return invocation.proceed();
        }
    }

    /**
     * 显示SQL执行耗时
     * @param sqlId
     * @param boundSql
     * @param configuration
     * @param executionTime
     * @param invocationResult
     */
    private void showSqlExecutionLog(String sqlId, BoundSql boundSql,
                                     Configuration configuration, long executionTime, Object invocationResult) {
        String sql = boundSql.getSql().replaceAll("\\s+", " ");
        executorService.submit(() -> {
            StringBuilder sqlExecutionLog = new StringBuilder();

            sqlExecutionLog.append("\n=======================================  SQL START  =======================================");

            // 是否显示原始SQL
            if(showOriginalSql) {
                sqlExecutionLog.append("\nSQL语句     ===>   ").append(sql);
                // 打印参数，模拟MyBatis官方日志格式
                String parameters = formatParameters(boundSql, configuration);
                if (!parameters.isEmpty()) {
                    sqlExecutionLog.append("\n").append(parameters);
                }
            }

            String completeSql = getCompleteSql(boundSql, configuration);
            // 是否显示格式化后的SQL
            if(showFormattedSql){
                // 格式化 SQL 语句，添加换行符
                completeSql = SqlFormatterUtils.format(completeSql);
            }
            sqlExecutionLog.append("\nSQL语句     ===>   ").append(completeSql);
            // 是否打印返回结果
            if(invocationResult != null){
                sqlExecutionLog.append("\nSQL结果     ===>   ").append(invocationResult);
            }
            sqlExecutionLog.append("\nSQL语句ID   ===>   ").append(sqlId);
            // 分析是否出现慢查询
            sqlExecutionLog.append("\n执行总用时   ===>   ").append(executionTime).append(" ms");
            sqlExecutionLog.append("\n慢查询阈值   ===>   ").append(longQueryTime).append(" ms");
            sqlExecutionLog.append("\n是否慢查询   ===>   ").append(executionTime > longQueryTime ? "是" : "否");
            sqlExecutionLog.append("\n=======================================  SQL   END  =======================================\n");

            // 分级日志输出
            log.debug("\033[31m{}\033[0m", sqlExecutionLog.toString());
        });
    }

    /**
     * 获取完整的带参数SQL语句
     * @param boundSql
     * @param configuration
     * @return
     */
    private String getCompleteSql(BoundSql boundSql, Configuration configuration) {
        String sql = boundSql.getSql();
        Object parameterObject = boundSql.getParameterObject();
        StringBuilder completeSql = new StringBuilder();
        try {
            // 获取参数映射列表
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            // 获取类型处理器注册表
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

            if (parameterMappings != null) {
                // 遍历参数映射
                for (int i = 0; i < parameterMappings.size(); i++) {
                    ParameterMapping parameterMapping = parameterMappings.get(i);
                    if (parameterMapping.getMode() != ParameterMode.OUT) {
                        // 获取参数名
                        String propertyName = parameterMapping.getProperty();
                        Object value;
                        // 获取参数值
                        if (boundSql.hasAdditionalParameter(propertyName)) {
                            value = boundSql.getAdditionalParameter(propertyName);
                        } else if (parameterObject == null) {
                            value = null;
                        } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                            value = parameterObject;
                        } else {
                            MetaObject metaObject = configuration.newMetaObject(parameterObject);
                            value = metaObject.getValue(propertyName);
                        }
                        // 格式化参数值
                        String formattedValue = getParameterValue(value, false);
                        // 替换SQL中的占位符
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(formattedValue));
                    }
                }
            }
        } catch (Exception e) {
            log.error("解析SQL参数失败: {}", e.getMessage(), e);
        }
        completeSql.append(sql);
        return completeSql.toString().replaceAll("\\s+", " ");
    }

    /**
     * 格式化参数对象，用于日志输出
     * @param parameterObject 参数对象
     * @return 格式化后的参数字符串
     */
    private String formatParameter(Object parameterObject) {
        if (parameterObject == null) {
            return "null";
        }
        
        // 如果是基本类型或其包装类型，直接返回值
        if (parameterObject instanceof String || parameterObject instanceof Number || 
            parameterObject instanceof Boolean || parameterObject instanceof Character ||
            parameterObject instanceof Date) {
            return getParameterValue(parameterObject, false);
        }

        // 对于其他对象，使用toString()方法并限制长度
        String paramString = parameterObject.toString();
        // 限制参数字符串长度，避免日志过长
        if (paramString.length() > 1000) {
            paramString = paramString.substring(0, 1000) + "...(truncated)";
        }
        return paramString;
    }

    /**
     * 格式化参数映射，模拟MyBatis官方日志格式
     * @param boundSql
     * @param configuration
     * @return 格式化后的参数映射字符串
     */
    private String formatParameters(BoundSql boundSql, Configuration configuration) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        
        if (parameterMappings == null || parameterMappings.isEmpty()) {
            return "";
        }
        
        StringBuilder paramsBuilder = new StringBuilder();
        paramsBuilder.append("SQL参数     ===>   ");

        try {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    String propertyName = parameterMapping.getProperty();
                    Object value;
                    
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }
                    
                    // 格式化参数值
                    String formattedValue = getParameterValue(value, true);
                    
                    // 添加参数类型信息
                    String typeName = value != null ? value.getClass().getSimpleName() : "null";
                    
                    if (i > 0) {
                        paramsBuilder.append(", ");
                    }
                    paramsBuilder.append(formattedValue).append("(").append(typeName).append(")");
                }
            }
        } catch (Exception e) {
            log.warn("解析参数失败: {}", e.getMessage());
            return "Parameters: unknown";
        }

        return paramsBuilder.toString();
    }

    /**
     * 格式化参数值
     * @param obj 参数对象
     * @param isInvokeInShowParameter 是否在显示参数值时调用
     * @return
     */
    private String getParameterValue(Object obj, boolean isInvokeInShowParameter) {
        String value;
        if (obj instanceof String) {
            value = isInvokeInShowParameter ? obj.toString() : "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(obj) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "null";
            }
        }
        return value;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 从配置加载参数
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        // 获取用户自定义的慢查询阈值
        String longQueryTimeUserDefined = properties.getProperty("longQueryTime");
        if (longQueryTimeUserDefined != null) {
            this.longQueryTime = Long.parseLong(longQueryTimeUserDefined);
        }

        // 获取用户自定义的是否显示原始SQL
        String showOriginalSql = properties.getProperty("showOriginalSql");
        if (showOriginalSql != null) {
            this.showOriginalSql = Boolean.parseBoolean(showOriginalSql);
        }

        // 获取用户自定义的是否显示格式化SQL
        String showFormattedSql = properties.getProperty("showFormattedSql");
        if (showFormattedSql != null) {
            this.showFormattedSql = Boolean.parseBoolean(showFormattedSql);
        }
    }

}