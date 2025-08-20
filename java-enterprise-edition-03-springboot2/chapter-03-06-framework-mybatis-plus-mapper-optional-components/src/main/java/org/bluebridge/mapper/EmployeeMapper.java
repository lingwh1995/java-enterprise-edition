package org.bluebridge.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.bluebridge.extend.base.MyBaseMapper;
import org.bluebridge.entity.Employee;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lingwh
 * @since 2025-02-27
 */
@Mapper
public interface EmployeeMapper extends MyBaseMapper<Employee> {

}

