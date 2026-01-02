package org.bluebridge.system.enums;

import lombok.Getter;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/31 23:06
 */
@Getter
public enum SlowSqlThresholdTypeEnum {

    /**
     * 数据库配置
     */
    DB("DB", "数据库中"),

    /**
     * YML 配置
     */
    YML("YML", "配置文件"),

    /**
     * 系统常量
     */
    CONSTANTS("CONSTANTS", "系统常量");

    /**
     * 类型
     */
    private String type;

    /**
     * 描述
     */
    private String desc;

    SlowSqlThresholdTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

}
