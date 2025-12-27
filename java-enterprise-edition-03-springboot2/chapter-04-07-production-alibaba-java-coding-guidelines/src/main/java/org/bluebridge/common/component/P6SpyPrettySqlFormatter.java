package org.bluebridge.common.component;

import cn.hutool.db.sql.SqlFormatter;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.bluebridge.common.constant.SqlConstants;
import org.bluebridge.common.enums.SqlHighlightColorEnum;
import org.bluebridge.common.enums.SqlShowFormattedStyleEnum;
import org.bluebridge.common.util.SqlFormatterUtils;

/**
 * @author lingwh
 * @desc 自定义P6Spy日志格式
 * @date 2025/12/27 0:20
 */
public class P6SpyPrettySqlFormatter implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        if (sql == null || sql.trim().isEmpty()) return "";

        // 移除多余的空格
        prepared = prepared.replaceAll("\\s+", " ").trim();
        sql = sql.replaceAll("\\s+", " ").trim();

        // 格式化SQL
        if(SqlConstants.SHOW_FORMATTED_SQL) {
            switch (SqlConstants.SQL_SHOW_FORMATTED_STYLE) {
                case SqlShowFormattedStyleEnum.HUTOOL:
                    prepared = SqlFormatter.format(prepared);
                    sql = SqlFormatter.format(sql);
                    break;
                case SqlShowFormattedStyleEnum.SELFT:
                    prepared = SqlFormatterUtils.format(prepared);
                    sql = SqlFormatterUtils.format(sql);
                    break;
                default:
                    prepared = SqlFormatter.format(prepared);
                    sql = SqlFormatter.format(sql);
                    break;
            }
        }

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("\n============================  SQL START  ============================");
        if(prepared.length() != sql.length()) {
            sqlBuilder.append("\n原始SQL   ===>   ").append("\n\t").append(prepared);
        }
        sqlBuilder.append("\n执行SQL   ===>   ").append("\n\t").append(sql);
        sqlBuilder.append("\n执行总耗时 ===>   ").append(elapsed).append(" ms");
        sqlBuilder.append("\n慢查询阈值 ===>   ").append(SqlConstants.LONG_QUERY_TIME).append(" ms");
        sqlBuilder.append("\n是否慢查询 ===>   ").append(elapsed > SqlConstants.LONG_QUERY_TIME ? "是" : "否");
        sqlBuilder.append("\n============================  SQL   END  ============================");

        // 高亮显示SQL
        switch (SqlConstants.SQL_HIGHLIGHT_COLOR) {
            // 红色字体显示sql
            case SqlHighlightColorEnum.RED:
                sql = "\033[31m" + sqlBuilder + "\033[0m";
                break;
            // 绿色字体显示sql
            case SqlHighlightColorEnum.GREEN:
                sql = "\033[32m" + sqlBuilder + "\033[0m";
                break;
            // 蓝色字体显示sql
            case SqlHighlightColorEnum.BLUE:
                sql = "\033[34m" + sqlBuilder + "\033[0m";
                break;
            default:
                break;
        }
        return sql;
    }

}