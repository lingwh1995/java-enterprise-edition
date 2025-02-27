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

    /**
     * 测试使用MybatisPlus的代码生成器生成的代码
     */
    @Test
    public void testMybatisPlusMpg() {
        List<Employee> employeeList = employeeController.getEmployeeList();
        employeeList.forEach(System.out::println);
    }

}