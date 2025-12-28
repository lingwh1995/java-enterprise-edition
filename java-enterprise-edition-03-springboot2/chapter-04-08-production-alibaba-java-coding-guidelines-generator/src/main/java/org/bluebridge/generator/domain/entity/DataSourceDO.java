package org.bluebridge.generator.domain.entity;

import org.bluebridge.common.domain.entity.BaseEntity;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author lingwh
 * @desc
 * @date 2025/11/23 12:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DataSourceDO extends BaseEntity {

    /** 数据源ID */
    private Integer id;

    /** 数据源名称 */
    @NotBlank(message = "数据源名称不能为空")
    private String name;

    /** 数据源类型 */
    @NotBlank(message = "数据源类型不能为空")
    private String type;

    /** 数据源host */
    @NotBlank(message = "数据源host不能为空")
    private String host;

    /** 数据源port */
    @Min(value = 0, message = "端口不能小于0")
    @Max(value = 65535, message = "端口不能大于65535")
    private Integer port;

    /** 数据源database */
    @NotBlank(message = "数据库名称不能为空")
    private String database;

    /** 数据源username */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /** 数据源password */
    @NotBlank(message = "密码不能为空")
    private String password;

    /** 数据源是否可用 */
    private boolean enable = false;

}
