package com.xa8bit.generator.domain.entity;

import lombok.Data;

/**
 * @author lingwh
 * @desc 列元信息
 * @date 2025/11/28
 */
@Data
public class ColumnMetaData {

    // 列名
    private String columnName;
    // 列类型
    private String columnType;
    // 列大小
    private Integer columnSize;
    // 小数位数
    private Integer decimalDigits;
    // 是否可为空
    private Boolean nullable;
    // 列注释
    private String columnComment;
    // 是否为主键
    private Boolean primaryKey;
    // 默认值
    private String defaultValue;

}