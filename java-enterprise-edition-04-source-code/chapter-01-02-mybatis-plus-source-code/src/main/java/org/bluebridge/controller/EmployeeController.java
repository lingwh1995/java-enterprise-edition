package org.bluebridge.controller;

import org.bluebridge.entity.Employee;
import org.bluebridge.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private IEmployeeService employeeService;

    public List<Employee> getEmployeeList(){
        return employeeService.selectList(null);
    }

}
