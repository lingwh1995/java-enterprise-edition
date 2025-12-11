package org.bluebridge.mapper;

import org.bluebridge.entity.Employee;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}

