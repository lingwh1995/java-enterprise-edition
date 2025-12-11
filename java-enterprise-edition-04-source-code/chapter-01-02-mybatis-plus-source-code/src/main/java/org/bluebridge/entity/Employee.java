package org.bluebridge.entity;


import lombok.Data;
import org.bluebridge.anno.TableName;

@Data
@TableName("t_employee")
public class Employee {

    private Long id;
    private String lastName;
    private String email;
    private String gender;
    private String deptNo;

}
