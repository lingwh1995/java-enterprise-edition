package org.bluebridge.demo.controller;

import org.bluebridge.demo.domain.Employee;
import org.bluebridge.demo.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;


@RequestMapping(value = "/restfulCase")
@Controller
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @PostMapping(value = "/employee")
    public String addEmployee(Employee employee) {
        employeeService.addEmployee(employee);
        return "redirect:/restfulCase/employee";
    }

    @DeleteMapping(value = "/employee/{id}")
    public String deleteEmployeeById(@PathVariable("id") Integer id) {
        employeeService.deleteEmployeeById(id);
        return "redirect:/restfulCase/employee";
    }

    @PutMapping(value = "/employee")
    public String updateEmployee(Employee employee) {
        employeeService.updateEmployee(employee);
        return "redirect:/restfulCase/employee";
    }

    @GetMapping(value = "/employee/{id}")
    public ModelAndView getEmployeeById(Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        employeeService.getEmployeeById(id);
        return modelAndView;
    }

    @GetMapping(value = "/employee")
    public ModelAndView getAllEmployees() {
        Collection<Employee> employees = employeeService.getAllEmployees();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("employeeList");
        modelAndView.addObject("employees",employees);
        return modelAndView;
    }

    /**
     * 跳转到编辑Employee页面
     * @param id
     * @return
     */
    @RequestMapping("/toEditEmployee/{id}")
    public ModelAndView toEditEmployee(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Employee employee = employeeService.getEmployeeById(id);
        modelAndView.setViewName("/editEmployee");
        modelAndView.addObject("employee",employee);
        return modelAndView;
    }
}
