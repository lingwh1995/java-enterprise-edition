package org.bluebridge.controller;

import org.bluebridge.entity.Employee;
import org.bluebridge.service.IEmployeeService;
import org.bluebridge.wrapper.QueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private IEmployeeService employeeService;

    /**
     * http://localhost:8080/employee/list
     * @return
     */
    @GetMapping("/list")
    public List<Employee> getEmployeeList(){
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>(Employee.class);
        queryWrapper.like("last_name", "张");
        List<Employee> employeeList = employeeService.list(queryWrapper);
        System.out.println("employeeList = " + employeeList);
        return employeeList;
    }

}
