package org.bluebridge.demo.service;


import org.bluebridge.demo.domain.Employee;

import java.util.Collection;

public interface IEmployeeService {
    void addEmployee(Employee employee);

    void deleteEmployeeById(Integer id);

    void updateEmployee(Employee employee);

    Employee getEmployeeById(Integer id);

    Collection<Employee> getAllEmployees();
}
