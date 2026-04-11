package org.bluebridge.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
/**
 * <p>
 * 
 * </p>
 *
 * @author lingwh
 * @since 2025-02-27
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("t_employee")
public class Employee {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("last_name")
    private String lastName;

    @TableField("email")
    private String email;

    @TableField("gender")
    private String gender;

    @TableField("dept_no")
    private String deptNo;
}
