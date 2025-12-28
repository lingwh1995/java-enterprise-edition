package com.xa8bit.system.constant;


import com.xa8bit.system.enums.SqlHighlightColorEnum;
import com.xa8bit.system.enums.SqlShowFormattedStyleEnum;

public class SqlConstants {

    /** 慢查询时间阈值（毫秒）*/
    public static final int LONG_QUERY_TIME = 1000;
    
    /** 是否格式化SQL */
    public static final boolean SHOW_FORMATTED_SQL = true;

    /** 格式化SQL样式 */
    public static final SqlShowFormattedStyleEnum SQL_SHOW_FORMATTED_STYLE = SqlShowFormattedStyleEnum.SELF;

    /** SQL高亮显示颜色 */
    public static final SqlHighlightColorEnum SQL_HIGHLIGHT_COLOR = SqlHighlightColorEnum.RED;

    /** P6Spy 格式化器类名 */
    public static final String P6SPY_FORMATTER_CLASS =
            SqlConstants.class.getPackage().getName().replace("constant", "component") + "." + "P6SpyPrettySqlFormatter";

}
