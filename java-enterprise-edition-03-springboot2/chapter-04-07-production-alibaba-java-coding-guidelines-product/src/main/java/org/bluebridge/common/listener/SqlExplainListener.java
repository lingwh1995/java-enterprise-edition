package org.bluebridge.common.listener;

import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.event.JdbcEventListener;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.common.constant.SqlConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author lingwh
 * @desc P6Spy SQL 分析监听器，自动分析 SELECT 语句的执行计划，识别全表扫描等风险
 * @date 2026/1/1 18:08
 */
@Slf4j
@Component
@ConditionalOnProperty(
        prefix = "decorator.datasource.p6spy",   // 配置前缀
        name = "enable-event-listener",          // 配置名称
        havingValue = "true",                    // 期望的值
        matchIfMissing = false                   // 如果 YML 没配，是否默认注入（false 表示不配就不注入）
)
public class SqlExplainListener extends JdbcEventListener {

    // 使用 ThreadLocal 防止 EXPLAIN 语句本身再次触发分析，导致死循环
    private static final ThreadLocal<Boolean> EXPLAIN_HOLDER = ThreadLocal.withInitial(() -> false);

    @Override
    public void onAfterExecute(PreparedStatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
        // 1. 基础校验：异常不分析、开关关闭不分析、递归调用不分析
        if (e != null || !SqlConstants.PRETTY_PRINT_ENABLED || EXPLAIN_HOLDER.get()) {
            return;
        }

        String sql = statementInformation.getSqlWithValues();
        if (sql == null || sql.isEmpty()) {
            return;
        }

        // 2. 只针对 SELECT 语句进行增强
        String trimmedSql = sql.trim().toLowerCase();
        if (trimmedSql.startsWith("select")) {
            try {
                EXPLAIN_HOLDER.set(true); // 标记开始分析
                explainSql(statementInformation.getConnectionInformation().getConnection(), sql, timeElapsedNanos);
            } finally {
                EXPLAIN_HOLDER.remove(); // 必须清除，防止影响线程后续请求
            }
        }
    }

    private void explainSql(Connection connection, String sql, long timeElapsedNanos) {
        // 建议仅对耗时较长或特定的SQL进行分析，减少性能损耗
        long elapsedMillis = timeElapsedNanos / 1_000_000;

        String explainSql = "EXPLAIN " + sql;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(explainSql)) {

            StringBuilder analysisResult = new StringBuilder();
            boolean isDanger = false;

            while (rs.next()) {
                String type = rs.getString("type");
                String key = rs.getString("key");
                String rows = rs.getString("rows");
                String extra = rs.getString("Extra");

                // 核心预警逻辑：全表扫描(ALL) 或 全索引扫描(index)
                if ("ALL".equalsIgnoreCase(type) || "index".equalsIgnoreCase(type)) {
                    isDanger = true;
                }

                analysisResult.append(String.format(
                        "\n[Plan] -> Type: %s, Key: %s, Rows: %s, Extra: %s",
                        type, (key == null ? "NULL" : key), rows, extra
                ));
            }

            // 3. 根据危险程度输出不同级别的日志
            if (isDanger) {
                log.warn("\n【SQL索引警告】检测到全表扫描或风险操作！" +
                        "\n执行耗时: {} ms" +
                        "\n原始 SQL: {}" +
                        "{}\n" +
                        "---------------------------------------", elapsedMillis, sql, analysisResult);
            } else if (elapsedMillis > SqlConstants.SLOW_SQL_THRESHOLD) {
                log.info("\n【慢SQL分析】耗时超过阈值 ({}ms): {}ms" +
                                "\nSQL: {}" +
                                "{}\n" +
                                "---------------------------------------",
                        SqlConstants.SLOW_SQL_THRESHOLD, elapsedMillis, sql, analysisResult);
            }else {
                // 暂无其他风险，不记录日志
            }

        } catch (Exception ex) {
            // 捕获所有异常，确保不影响主业务 SQL 的提交/回滚
            log.error("[P6Spy] 自动执行 EXPLAIN 失败，SQL: {}", sql, ex);
        }
    }
}