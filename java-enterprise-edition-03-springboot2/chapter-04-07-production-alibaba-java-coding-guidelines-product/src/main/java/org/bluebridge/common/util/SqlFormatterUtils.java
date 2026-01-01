package org.bluebridge.common.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.sql.SqlFormatter;
import org.bluebridge.common.constant.SqlConstants;

/**
 * @author lingwh
 * @desc SQL格式化工具类
 * @date 2025/12/27 20:16
 */
public class SqlFormatterUtils {

    /**
     * 格式化SQL语句
     * @param sql
     * @return
     */
    public static String format(String sql) {
        sql = cleanSql(sql);

        if (SqlConstants.PRETTY_PRINT_ENABLED) {
            switch (SqlConstants.PRETTY_PRINT_STYLE) {
                case HUTOOL:
                    sql = formatSqlByHutool(sql);
                    break;
                case SELF:
                    sql = formatSqlBySelf(sql);
                    break;
                default:
                    sql = SqlFormatter.format(sql);
                    break;
            }
        }
        return sql;
    }

    /**
     * 清洗 SQL：去除换行、回车、制表符，并将多个连续空格压缩为一个
     * @param sql 原始乱序 SQL
     * @return 紧凑的一行 SQL
     */
    private static String cleanSql(String sql) {
        if (StrUtil.isBlank(sql)) return sql;

        return sql.replaceAll("[\\r\\n\\t]", " ") // 替换换行符、回车符、制表符为空格
                .replaceAll("\\s+", " ")        // 将多个连续空格合并为一个
                .trim();                        // 去除首尾空格
    }

    /**
     * 使用Hutool格式化SQL语句
     * @param sql
     * @return
     */
    private static String formatSqlByHutool(String sql) {
        return SqlFormatter.format(sql);
    }

    /**
     * 手动简单美化SQL语句
     * @param sql
     * @return
     */
    private static String formatSqlBySelf(String sql) {
        if (StrUtil.isBlank(sql)) return sql;

        // 1. 先进行基础格式化（不处理 AND）
        sql = sql.trim()
                .replaceAll("(?i)SELECT ", "SELECT\n        ")
                .replaceAll("(?i) FROM ", "\n    FROM ")
                .replaceAll("(?i) count\\(", "COUNT(")
                .replaceAll("(?i) WHERE ", "\n    WHERE ")
                .replaceAll("(?i) LIKE ", " LIKE ")
                .replaceAll("(?i) AND ", " AND ")
                .replaceAll("(?i) LEFT JOIN ", "\n    LEFT JOIN ")
                .replaceAll("(?i) RIGHT JOIN ", "\n    RIGHT JOIN ")
                .replaceAll("(?i) INNER JOIN ", "\n    INNER JOIN ")
                .replaceAll("(?i) ON ", " ON ")
                .replaceAll("(?i) ORDER BY ", "\n    ORDER BY ")
                .replaceAll("(?i) GROUP BY ", "\n    GROUP BY ")
                .replaceAll("(?i) LIMIT ", "\n    LIMIT ")
                .replaceAll("(?i) UPDATE ", "UPDATE ")
                .replaceAll("(?i) SET ", "\n    SET ");

        // 2. 针对 SELECT 和 FROM 之间的字段列表进行长文本换行处理
        String upperSql = sql.toUpperCase();
        int selectIndex = upperSql.indexOf("SELECT");
        int fromIndex = upperSql.indexOf("FROM");
        if (selectIndex != -1 && fromIndex != -1 && fromIndex > selectIndex) {
            // 1. 提取字段部分
            String fieldsPart = sql.substring(selectIndex + 6, fromIndex).trim();

            // 2. 设定阈值：总长度超过 50 才处理，每行最大宽度设定为 60
            if (fieldsPart.length() > 50) {
                String[] fields = fieldsPart.split(",\\s*");
                StringBuilder sb = new StringBuilder("\n        "); // 初始换行并缩进
                int currentLineLength = 8; // 初始缩进占据 8 个空格
                int maxRowWidth = 60;      // 每行大概显示的字符数限制

                for (int i = 0; i < fields.length; i++) {
                    String field = fields[i];
                    // 拼接时判断：如果不是最后一个字段，加上逗号和空格
                    String appendStr = (i == fields.length - 1) ? field : field + ", ";

                    // 核心逻辑：如果当前行加上这个字段超过了最大宽度，就先换行
                    if (currentLineLength + appendStr.length() > maxRowWidth) {
                        sb.append("\n        "); // 换行
                        currentLineLength = 8;   // 重置当前行长度计数
                    }

                    sb.append(appendStr);
                    currentLineLength += appendStr.length();
                }

                // 3. 拼接回原 SQL
                sql = sql.substring(0, selectIndex + 6)
                        + sb.toString()
                        + "\n    " + sql.substring(fromIndex);
            }
        }

        // 3. 关键逻辑：找到 WHERE 的位置，仅对 WHERE 之后的内容进行 AND 换行处理
        int whereIndex = sql.toUpperCase().lastIndexOf("WHERE");
        if (whereIndex != -1) {
            String beforeWhere = sql.substring(0, whereIndex + 5);
            String afterWhere = sql.substring(whereIndex + 5);

            // 只有在 WHERE 之后的 AND 才会被替换
            // 并且我们限制只替换到下一个主关键字（如 ORDER BY）之前的内容
            // 这里简单处理：对 WHERE 后的所有 AND 换行
            afterWhere = afterWhere.replaceAll("(?i)\\s+\\bAND\\b", "\n      AND");

            sql = beforeWhere + afterWhere;
        }

        // 4. 格式化SQL 专门针对 INSERT INTO 结构的格式化
        if (sql.toUpperCase().startsWith("INSERT INTO")) {
            sql = sql.trim()
                .replaceAll("(?i)INSERT INTO ", "INSERT INTO ")
                // 在表名后的左括号前换行
                .replaceAll("(?i)\\s+\\(", "\n        (")
                // 处理 VALUES 关键字换行
                .replaceAll("(?i)\\s+VALUES\\s+", "\n    VALUES ")
                // 处理 VALUES 后的左括号换行
                .replaceAll("(?i)VALUES\\s+\\(", "VALUES\n        (");
        }

        return sql;
    }

}
