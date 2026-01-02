package org.bluebridge.system.processor;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.system.constant.SqlConstants;
import org.bluebridge.system.enums.SlowSqlThresholdTypeEnum;
import org.bluebridge.system.enums.SqlHighlightColorEnum;
import org.bluebridge.system.enums.SqlShowFormattedStyleEnum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author lingwh
 * @desc SQL常量配置处理器
 * @date 2025/12/31 17:53
 */
@Slf4j
public class SqlConstantsProcessor implements EnvironmentPostProcessor {

    private static final String PREFIX = "boost.sql.";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // 直接设置系统属性，在这里设置优先级别很高，可以解决yml中配置不生效的问题
        System.setProperty("p6spy.config.logMessageFormat", SqlConstants.P6SPY_FORMATTER_CLASS);

        // 1. 基本类型：使用 Optional 或 getProperty 的默认值功能
        try {
            SqlConstants.SLOW_SQL_THRESHOLD = environment.getProperty(
                    PREFIX + "slow-sql-threshold", Integer.class, SqlConstants.SLOW_SQL_THRESHOLD);
        } catch (ConversionFailedException e) {
            SqlConstants.SLOW_SQL_THRESHOLD_TYPE = SlowSqlThresholdTypeEnum.CONSTANTS;
            log.debug("Invalid SqlConstants.SLOW_SQL_THRESHOLD configuration: {}, using default.",
                    environment.getProperty(PREFIX + "slow-sql-threshold"), e);
        }

        try {
            SqlConstants.SHOW_ORIGINAL_SQL = environment.getProperty(
                    PREFIX + "show-original-sql", Boolean.class, SqlConstants.SHOW_ORIGINAL_SQL);
        } catch (ConversionFailedException e) {
            log.debug("Invalid SqlConstants.SHOW_ORIGINAL_SQL configuration: {}, using default.",
                    environment.getProperty(PREFIX + "show-original-sql"), e);
        }

        try {
            SqlConstants.PRETTY_PRINT_ENABLED = environment.getProperty(
                    PREFIX + "pretty-print-enabled", Boolean.class, SqlConstants.PRETTY_PRINT_ENABLED);
        } catch (ConversionFailedException e) {
            log.debug("Invalid SqlConstants.PRETTY_PRINT_ENABLED configuration: {}, using default.",
                    environment.getProperty(PREFIX + "pretty-print-enabled"), e);
        }


        // 2. 枚举类型：增加判空和异常捕获，防止用户配置错误导致程序启动失败
        // 处理格式化样式枚举
        String styleStr = environment.getProperty(PREFIX + "pretty-print-style");
        if (StrUtil.isNotBlank(styleStr)) {
            try {
                SqlConstants.PRETTY_PRINT_STYLE = SqlShowFormattedStyleEnum.valueOf(styleStr.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                log.debug("Invalid SqlConstants.PRETTY_PRINT_STYLE configuration: {}, using default.", styleStr, e);
            }
        }

        // 处理颜色枚举
        String colorStr = environment.getProperty(PREFIX + "pretty-print-color");
        if (StrUtil.isNotBlank(colorStr)) {
            try {
                SqlConstants.PRETTY_PRINT_COLOR = SqlHighlightColorEnum.valueOf(colorStr.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                log.debug("Invalid SqlConstants.PRETTY_PRINT_COLOR configuration: {}, using default.", colorStr, e);
            }
        }
    }

}