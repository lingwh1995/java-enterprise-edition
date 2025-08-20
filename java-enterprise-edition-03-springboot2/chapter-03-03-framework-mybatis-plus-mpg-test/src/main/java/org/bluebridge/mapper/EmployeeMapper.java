package org.bluebridge.mapper;

import org.bluebridge.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lingwh
 * @since 2025-02-27
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}

