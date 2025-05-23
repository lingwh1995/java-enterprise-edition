package org.bluebridge.ssm.controller;

import org.bluebridge.ssm.domain.Emp;
import org.bluebridge.ssm.service.IEmpServices;
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
