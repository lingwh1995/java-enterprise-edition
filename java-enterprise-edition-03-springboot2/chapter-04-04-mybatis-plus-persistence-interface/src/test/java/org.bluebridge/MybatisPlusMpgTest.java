package org.bluebridge;

import org.bluebridge.controller.EmployeeController;
import org.bluebridge.entity.Employee;
import org.bluebridge.mapper.EmployeeMapper;
import org.bluebridge.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MybatisPlusMpgTest {

    @Autowired
    private IEmployeeService employeeService;

    @Test
    public void testSelect() {
//        employeeService.save()
    }

}