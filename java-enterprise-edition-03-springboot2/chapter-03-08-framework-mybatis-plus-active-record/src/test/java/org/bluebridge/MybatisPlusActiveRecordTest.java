package org.bluebridge;

import org.bluebridge.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class MybatisPlusActiveRecordTest {

    /**
     * 测试插入单个用户
     */
    @Test
    public void testInsert() {
        // 创建新Employee并插入数据库
        Employee employee = Employee.builder()
                .id(1l)
                .email("1111111111@qq.com")
                .gender("男")
                .lastName("张一")
                .deptNo("01")
                .build();
        boolean insert = employee.insert();
        System.out.println("insert = " + insert);
    }

    /**
     * 查询所有用户
     */
    @Test
    public void testSelectAll() {
        Employee employee = new Employee();
        // 查询所有用户
        List<Employee> employeeList = employee.selectAll();
        System.out.println("employeeList = " + employeeList);
    }

    /**
     * 测试根据 ID 更新Employee
     */
    @Test
    public void testUpdateEmployeeById() {
        Employee employee = new Employee();
        employee.setId(1l);
        employee.setLastName("张一一");
        // 返回值表示操作是否成功
        boolean isUpdated = employee.updateById();
        System.out.println("isUpdated = " + isUpdated);
    }

    /**
     * 测试根据 ID 删除Employee
     */
    @Test
    public void testDeleteEmployeeById() {
        Employee employee = new Employee();
        boolean isDelete = employee.deleteById(1l);
        System.out.println("isDelete = " + isDelete);
    }

}
