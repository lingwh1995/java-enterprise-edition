package org.bluebridge.generator.domain.dto;

import lombok.Data;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/22 14:15
 */
@Data
public class DataSourceUpdateDTO {

    /** 数据源名称 */
    private String name;

    /** 数据源类型 */
    private String type;

    /** 数据源host */
    private String host;

    /** 数据源port */
    private String port;

    /** 数据源database */
    private String database;

    /** 数据源username */
    private String username;

    /** 数据源password */
    private String password;

    /** 数据源是否可用 */
    private boolean enable;

}
