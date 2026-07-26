package org.bluebridge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.bluebridge.entity.Employee;

/**
 * 员工 Mapper 接口
 *
 * @author lingwh
 * @date 2025/2/27 17:05
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
