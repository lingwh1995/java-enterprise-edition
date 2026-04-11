package org.bluebridge;

import org.assertj.core.util.Lists;
import org.bluebridge.entity.Employee;
import org.bluebridge.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MybatisPlusInjectorTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    public void testInsert() {
        for (int i = 0; i < 2; i++) {
            Employee employee = Employee.builder()
                    .email("1111111111@qq.com")
                    .gender("男")
                    .lastName("张" + i)
                    .deptNo("01")
                    .build();
            employeeMapper.insert(employee);
        }
    }

    @Test
    public void testInsertBatchSomeColumn() {
        List<Employee> employeeList = Lists.newArrayList();
        for (int i = 2; i < 20; i++) {
            Employee employee = Employee.builder()
                    .email("1111111111@qq.com")
                    .gender("男")
                    .lastName("张" + i)
                    .deptNo("01")
                    .build();
            employeeList.add(employee);
        }
        employeeMapper.insertBatchSomeColumn(employeeList);
    }

    @Test
    public void testDeleteByIdWithFill() {
        Employee employee = employeeMapper.selectById(1l);
        int i = employeeMapper.deleteByIdWithFill(employee);
        System.out.println("i = " + i);
    }

    @Test
    public void testDeleteAll() {
        employeeMapper.deleteAll();
    }
}
