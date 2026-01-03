package org.bluebridge.common.constant;

import org.bluebridge.common.enums.SlowSqlThresholdTypeEnum;
import org.bluebridge.common.enums.SqlHighlightColorEnum;
import org.bluebridge.common.enums.SqlShowFormattedStyleEnum;

/**
 * @author lingwh
 * @desc SQL 配置常量
 * @date 2025/1/03 18:59
 */
public final class SqlConstants {

    /**
     * SQL 配置慢查询时间阈值（毫秒）
     */
    public static int SLOW_SQL_THRESHOLD = 1000;

    /**
     * 是否打印未填充参数的原始SQL
     */
    public static boolean SHOW_ORIGINAL_SQL = true;

    /**
     * SQL 配置是否格式化打印SQL
     */
    public static boolean PRETTY_PRINT_ENABLED = true;

    /**
     * SQL 配置格式化打印SQL样式
     */
    public static SqlShowFormattedStyleEnum PRETTY_PRINT_STYLE = SqlShowFormattedStyleEnum.HUTOOL;

    /**
     * SQL 配置格式化打印SQL颜色
     */
    public static SqlHighlightColorEnum PRETTY_PRINT_COLOR = SqlHighlightColorEnum.BLUE;

    /**
     * SQL 配置慢查询时间阈值参数类型
     */
    public static SlowSqlThresholdTypeEnum SLOW_SQL_THRESHOLD_TYPE = SlowSqlThresholdTypeEnum.YML;

    /**
     * P6Spy 格式化器类名
     */
    public static final String P6SPY_FORMATTER_CLASS =
            SqlConstants.class.getPackage().getName().replace("constant", "component") + "." + "PrettyPrintFormat";

}
