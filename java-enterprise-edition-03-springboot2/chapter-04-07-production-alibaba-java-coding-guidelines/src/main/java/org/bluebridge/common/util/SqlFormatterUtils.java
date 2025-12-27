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
        return sql.trim()
                .replaceAll("(?i)SELECT ", "SELECT ")
                .replaceAll("(?i) FROM ", "\n    FROM ")
                .replaceAll("(?i) WHERE ", "\n    WHERE ")
                .replaceAll("(?i) AND ", "\n      AND ")
                .replaceAll("(?i) ORDER BY ", "\n    ORDER BY ")
                .replaceAll("(?i) GROUP BY ", "\n    GROUP BY ")
                .replaceAll("(?i) LIMIT ", "\n    LIMIT ");
    }

}
