package org.bluebridge.monitor.component;

import cn.hutool.core.util.StrUtil;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.bluebridge.monitor.constant.SqlConstants;
import org.bluebridge.monitor.util.PrintUtils;
import org.bluebridge.monitor.util.SqlFormatterUtils;

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
        prepared = SqlFormatterUtils.format(prepared);
        sql = SqlFormatterUtils.format(sql);

        // 4. 构建SQL日志
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("\n--------------------------------------  SQL     START  --------------------------------------");
        if(SqlConstants.SHOW_ORIGINAL_SQL && prepared.length() != sql.length()) {
            sqlBuilder.append("\n原始SQL   --->   ").append("\n\t").append(prepared);
        }
        sqlBuilder.append("\n执行SQL   --->   ").append("\n\t").append(sql);
        sqlBuilder.append("\n执行总耗时 --->   ").append(elapsed).append(" ms");
        sqlBuilder.append("\n慢查询阈值 --->   ").append(SqlConstants.SLOW_SQL_THRESHOLD).append(" ms");
        sqlBuilder.append("\n慢阈值来源 --->   ").append(SqlConstants.SLOW_SQL_THRESHOLD_TYPE.getType());
        sqlBuilder.append("\n是否慢查询 --->   ").append(elapsed > SqlConstants.SLOW_SQL_THRESHOLD ? "是" : "否");
        sqlBuilder.append("\n--------------------------------------  SQL       END  --------------------------------------");

        // 5. 高亮显示SQL日志
        sql = PrintUtils.highlight(sqlBuilder.toString());

        // 6. 返回格式化并高亮后的SQL日志
        return sql;
    }

}