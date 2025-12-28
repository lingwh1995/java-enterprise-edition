package com.xa8bit.generator.domain.entity;

import lombok.Data;
import java.util.List;

/**
 * @author lingwh
 * @desc 表元信息
 * @date 2025/11/28
 */
@Data
public class TableMetaData {

    // 表名
    private String tableName;
    // 表注释
    private String tableComment;
    // 列信息列表
    private List<ColumnMetaData> columns;
}