package org.bluebridge.generator.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/22 14:51
 */
@Data
public class DataSourceVO {

    /** 数据源ID */
    private Integer id;

    /** 数据源名称 */
    private String name;

    /** 数据源类型 */
    private String type;

    /** 数据源host */
    private String host;

    /** 数据源port */
    private Integer port;

    /** 数据源database */
    private String database;

    /** 数据源username */
    private String username;

    /** 数据源password */
    private String password;

    /** 数据源是否可用 */
    private boolean enable = false;

    /** 数据源创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /** 数据源更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
