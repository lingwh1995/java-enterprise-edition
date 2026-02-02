package org.bluebridge.monitor.util;

import org.bluebridge.monitor.constant.SqlConstants;

/**
 * @author lingwh
 * @desc  打印工具类
 * @date 2026/1/1 20:48
 */
public class PrintUtils {

    /**
     * 获取高亮日志
     * @param log
     * @return
     */
    public static String highlight(String log) {
        switch (SqlConstants.PRETTY_PRINT_COLOR) {
            // 红色字体显示sql
            case RED:
                log = "\033[31m" + log + "\033[0m";
                break;
            // 绿色字体显示sql
            case GREEN:
                log = "\033[32m" + log + "\033[0m";
                break;
            // 蓝色字体显示sql
            case BLUE:
                log = "\033[34m" + log + "\033[0m";
                break;
            default:
                log = log.toString();
                break;
        }
        return log;
    }

}
