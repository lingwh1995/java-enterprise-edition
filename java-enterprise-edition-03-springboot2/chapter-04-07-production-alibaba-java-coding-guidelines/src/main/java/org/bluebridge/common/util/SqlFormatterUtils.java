package org.bluebridge.common.util;

/**
 * @author lingwh
 * @desc SQL语句格式化工具类
 * @date 2025/12/20 16:00
 */
public class SqlFormatterUtils {

    /**
     * 简单格式化SQL语句，使其更易读
     *
     * @param sql 原始SQL语句
     * @return 格式化后的SQL语句
     */
    public static String format(String sql) {
        if (sql == null || sql.trim().isEmpty()) {
            return sql;
        }

        // 清理多余的空白字符，保留单个空格
        String formattedSql = sql.replaceAll("\\s+", " ").trim();

        // SELECT语句关键字垂直对齐处理
        formattedSql = formattedSql.replaceAll("(?i)\\s+(SELECT)\\s+", "\n$1 ");
        formattedSql = formattedSql.replaceAll("(?i)\\s+(FROM)\\s+", "\n$1 ");
        formattedSql = formattedSql.replaceAll("(?i)\\s+(WHERE)\\s+", "\n$1 ");
        formattedSql = formattedSql.replaceAll("(?i)\\s+(ORDER BY)\\s+", "\n$1 ");
        formattedSql = formattedSql.replaceAll("(?i)\\s+(GROUP BY)\\s+", "\n$1 ");
        formattedSql = formattedSql.replaceAll("(?i)\\s+(HAVING)\\s+", "\n$1 ");

        // INSERT语句关键字垂直对齐处理
        formattedSql = formattedSql.replaceAll("(?i)\\s+(INSERT INTO)\\s+", "\n$1 ");
        formattedSql = formattedSql.replaceAll("(?i)\\s+(VALUES)\\s+", "\n$1 ");

        // UPDATE语句关键字垂直对齐处理
        formattedSql = formattedSql.replaceAll("(?i)\\s+(UPDATE)\\s+", "\n$1 ");
        formattedSql = formattedSql.replaceAll("(?i)\\s+(SET)\\s+", "\n$1 ");

        // DELETE语句关键字垂直对齐处理
        formattedSql = formattedSql.replaceAll("(?i)\\s+(DELETE FROM)\\s+", "\n$1 ");

        // 逻辑操作符对齐处理
        formattedSql = formattedSql.replaceAll("(?i)\\s+(AND)\\s+", "\n$1 ");
        formattedSql = formattedSql.replaceAll("(?i)\\s+(OR)\\s+", "\n$1 ");

        // 处理逗号分隔，保持列对齐
        formattedSql = formattedSql.replaceAll(",(?=\\s*[a-zA-Z_])", ",\n    ");

        // 处理左括号
        formattedSql = formattedSql.replaceAll("\\((?=\\s*[a-zA-Z_])", "(\n    ");

        // 处理右括号
        formattedSql = formattedSql.replaceAll("(?<=[a-zA-Z0-9_])\\)", "\n)");

        return formattedSql.trim();
    }
}