package org.bluebridge.common.component;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.sql.SqlFormatter;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.bluebridge.common.constant.SqlConstants;
import org.bluebridge.common.util.SqlFormatterUtils;

/**
 * @author lingwh
 * @desc 自定义P6Spy日志格式
 * @date 2025/12/27 0:20
 */
public class PrettyPrintFormat implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        // 1. 判断SQL是否合法
        if (StrUtil.isBlank(sql)) {
            return "";
        }

        // 2.移除多余的空格
        prepared = prepared.replaceAll("\\s+", " ").trim();
        sql = sql.replaceAll("\\s+", " ").trim();

        // 3.格式化SQL
        if(SqlConstants.PRETTY_PRINT_ENABLED) {
            switch (SqlConstants.PRETTY_PRINT_STYLE) {
                case HUTOOL:
                    prepared = SqlFormatter.format(prepared);
                    sql = SqlFormatter.format(sql);
                    break;
                case SELF:
                    prepared = SqlFormatterUtils.format(prepared);
                    sql = SqlFormatterUtils.format(sql);
                    break;
                default:
                    prepared = SqlFormatter.format(prepared);
                    sql = SqlFormatter.format(sql);
                    break;
            }
        }

        // 4. 构建SQL日志
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("\n============================  SQL START  ============================");
        if(SqlConstants.SHOW_ORIGINAL_SQL && prepared.length() != sql.length()) {
            sqlBuilder.append("\n原始SQL   ===>   ").append("\n\t").append(prepared);
        }
        sqlBuilder.append("\n执行SQL   ===>   ").append("\n\t").append(sql);
        sqlBuilder.append("\n执行总耗时 ===>   ").append(elapsed).append(" ms");
        sqlBuilder.append("\n慢查询阈值 ===>   ").append(SqlConstants.SLOW_SQL_THRESHOLD).append(" ms");
        sqlBuilder.append("\n慢阈值来源 ===>   ").append(SqlConstants.SLOW_SQL_THRESHOLD_TYPE.getType());
        sqlBuilder.append("\n是否慢查询 ===>   ").append(elapsed > SqlConstants.SLOW_SQL_THRESHOLD ? "是" : "否");
        sqlBuilder.append("\n============================  SQL   END  ============================");

        // 5.高亮显示SQL
        switch (SqlConstants.PRETTY_PRINT_COLOR) {
            // 红色字体显示sql
            case RED:
                sql = "\033[31m" + sqlBuilder + "\033[0m";
                break;
            // 绿色字体显示sql
            case GREEN:
                sql = "\033[32m" + sqlBuilder + "\033[0m";
                break;
            // 蓝色字体显示sql
            case BLUE:
                sql = "\033[34m" + sqlBuilder + "\033[0m";
                break;
            default:
                sql = sqlBuilder.toString();
                break;
        }
        return sql;
    }

}