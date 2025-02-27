package org.bluebridge.controller;

import org.bluebridge.entity.Employee;
import org.bluebridge.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lingwh
 * @since 2025-02-27
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    public List<Employee> getEmployeeList(){
        return employeeService.list();
    }
}
