package org.bluebridge.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.bluebridge.entity.Employee;

/**
 * 员工 Mapper 接口
 *
 * @author lingwh
 * @date 2025/12/10 11:20
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
