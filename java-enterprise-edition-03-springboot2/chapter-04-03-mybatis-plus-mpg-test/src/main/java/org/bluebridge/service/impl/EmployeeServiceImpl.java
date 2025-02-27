package org.bluebridge.service.impl;

import org.bluebridge.entity.Employee;
import org.bluebridge.mapper.EmployeeMapper;
import org.bluebridge.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lingwh
 * @since 2025-02-27
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
