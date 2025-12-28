package org.bluebridge.common.util;

/**
 * @author lingwh
 * @desc SQL格式化工具类
 * @date 2025/12/27 20:16
 */
public class SqlFormatterUtils {

    /**
     * 手动简单美化
     * @param sql
     * @return
     */
    public static String format(String sql) {
        if (sql == null || sql.isEmpty()) return sql;

        // 1. 先进行基础格式化（不处理 AND）
        String formatted = sql.trim()
                .replaceAll("(?i)SELECT ", "SELECT\n        ")
                .replaceAll("(?i) FROM ", "\n    FROM ")
                .replaceAll("(?i) WHERE ", "\n    WHERE ")
                .replaceAll("(?i) AND ", " AND ")
                .replaceAll("(?i) LEFT JOIN ", "\n    LEFT JOIN ")
                .replaceAll("(?i) RIGHT JOIN ", "\n    RIGHT JOIN ")
                .replaceAll("(?i) INNER JOIN ", "\n    INNER JOIN ")
                .replaceAll("(?i) ON ", " ON ")
                .replaceAll("(?i) ORDER BY ", "\n    ORDER BY ")
                .replaceAll("(?i) GROUP BY ", "\n    GROUP BY ")
                .replaceAll("(?i) LIMIT ", "\n    LIMIT ");


        // 2. 关键逻辑：找到 WHERE 的位置，仅对 WHERE 之后的内容进行 AND 换行处理
        int whereIndex = formatted.toUpperCase().lastIndexOf("WHERE");
        if (whereIndex != -1) {
            String beforeWhere = formatted.substring(0, whereIndex + 5);
            String afterWhere = formatted.substring(whereIndex + 5);

            // 只有在 WHERE 之后的 AND 才会被替换
            // 并且我们限制只替换到下一个主关键字（如 ORDER BY）之前的内容
            // 这里简单处理：对 WHERE 后的所有 AND 换行
            afterWhere = afterWhere.replaceAll("(?i)\\s+\\bAND\\b", "\n      AND");

            formatted = beforeWhere + afterWhere;
        }

        return formatted;
    }

}
