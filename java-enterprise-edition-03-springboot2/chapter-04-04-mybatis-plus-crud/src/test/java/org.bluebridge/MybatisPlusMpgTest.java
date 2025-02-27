package org.bluebridge;

import org.bluebridge.controller.EmployeeController;
import org.bluebridge.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MybatisPlusMpgTest {

    @Autowired
    private EmployeeController employeeController;

    @Test
    public void testSelect() {
        List<Employee> employeeList = employeeController.getEmployeeList();
        employeeList.forEach(System.out::println);
    }

}