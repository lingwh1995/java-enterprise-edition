package org.bluebridge.entity;


import lombok.Data;

@Data
public class Employee {

    private Long id;
    private String lastName;
    private String email;
    private String gender;
    private String deptNo;

}
