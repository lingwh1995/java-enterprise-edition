package org.bluebridge.generator.domain.entity;

import lombok.Data;
import java.util.List;

/**
 * @author lingwh
 * @desc 数据库元信息
 * @date 2025/11/28
 */
@Data
public class DataSourceMetaData {

    // 数据库名称
    private String database;
    // 数据库类型
    private String type;
    // 数据库版本
    private String version;
    // 表信息列表
    private List<TableMetaData> tableMetaDataList;

}