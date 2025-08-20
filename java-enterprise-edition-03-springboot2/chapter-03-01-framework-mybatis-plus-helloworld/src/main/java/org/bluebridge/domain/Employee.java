package org.bluebridge.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("`t_employee`")
public class Employee {
    private Long id;
    //private String lastName;
    private String email;
    private String gender;
}