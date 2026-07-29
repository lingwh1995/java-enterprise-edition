package org.bluebridge.monitor.util;
import cn.hutool.db.sql.SqlFormatter;
import org.bluebridge.monitor.constant.SqlConstants;

/**
 * SQL 格式化工具类
 *
 * @author lingwh
 * @date 2025/12/27 20:16
 */
public class SqlFormatterUtils {

    /**
     * 格式化 SQL 语句
     *
     * @param sql
     * @return
     */
    public static String format(String sql) {
        if (SqlConstants.PRETTY_PRINT_ENABLED) {
            switch (SqlConstants.PRETTY_PRINT_STYLE) {
                case HUTOOL:
                    sql = formatSqlByHutool(sql);
                    break;
                default:
                    sql = formatSqlByHutool(sql);
                    break;
            }
        }
        return sql;
    }

    /**
     * 使用 Hutool 格式化 SQL 语句
     *
     * @param sql
     * @return
     */
    private static String formatSqlByHutool(String sql) {
        return SqlFormatter.format(sql);
    }
}
