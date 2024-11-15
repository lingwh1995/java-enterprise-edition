package org.bluebridge.controller;

import org.bluebridge.dao.DepartmentDao;
import org.bluebridge.dao.EmployeeDao;
import org.bluebridge.entities.Department;
import org.bluebridge.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author ronin
 */
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeDao employeDao;

    @Autowired
    private DepartmentDao departmentDao;

    /**
     * 查询所有员工返回列表页面
     * @return
     */
    @GetMapping("/emps")
    public String list(Model model){
        Collection<Employee> employees = employeDao.getAll();
        model.addAttribute("emps",employees);
        return "emp/list";
    }

    @GetMapping("/emp")
    public String toAddPage(Model model){
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts", departments);
        return "emp/add";
    }

    @PostMapping("/emp")
    public String addPage(Employee employee){
        System.out.println("保存的用户信息:"+employee);
        employeDao.save(employee);
        return "redirect:/emps";
    }

    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id,Model model){
        Employee employee = employeDao.get(id);
        model.addAttribute("emp",employee);
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts", departments);
        return "emp/edit";
    }

    @PutMapping("/emp")
    public String editPage(Employee employee){
        employeDao.save(employee);
        return "redirect:/emps";
    }

    @DeleteMapping("/emp/{id}")
    public String delete(@PathVariable("id") Integer id){
        employeDao.delete(id);
        return "redirect:/emps";
    }
}
