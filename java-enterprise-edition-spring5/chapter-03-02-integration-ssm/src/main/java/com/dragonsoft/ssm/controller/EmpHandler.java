package com.dragonsoft.ssm.controller;

import com.dragonsoft.ssm.domain.Emp;
import com.dragonsoft.ssm.service.IEmpServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmpHandler {
    @Autowired
    private IEmpServices empServices;

    @RequestMapping("testSsm")
    public String testSsm(){
        Emp employee = empServices.getEmployeeById("001");
        System.out.println(employee);
        return "success";
    }
}
