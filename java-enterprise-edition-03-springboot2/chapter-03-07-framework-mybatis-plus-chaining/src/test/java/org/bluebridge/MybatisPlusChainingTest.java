package org.bluebridge;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.bluebridge.entity.Employee;
import org.bluebridge.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static com.baomidou.mybatisplus.extension.toolkit.Db.*;


@SpringBootTest
public class MybatisPlusChainingTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    public void init() {
        List<Employee> employeeList = Arrays.asList(
                new Employee(1l,"张一", "1111111111@qq.com", "男", "01"),
                new Employee(2l,"张二", "2222222222@qq.com", "女", "02"),
                new Employee(3l,"张三", "3333333333@qq.com", "男", "03"),
                new Employee(4l,"张四", "44444444444@qq.com", "男", "03"),
                new Employee(5l,"张五", "55555555555@qq.com", "男", "03"),
                new Employee(6l,"张六", "66666666666@qq.com", "男", "03")
        );
        employeeList.forEach(employee -> {
            employeeMapper.insert(employee);
        });
    }

    @Test
    public void testChainingQuery() {
        // 普通链式查询示例
        List<Employee> list = query(Employee.class).eq("gender", "男").list();
        System.out.println("list = " + list);

        // lambda 链式查询示例
        Employee employee = lambdaQuery(Employee.class).eq(Employee::getLastName, "张一").one();
        System.out.println("employee = " + employee);
    }

    @Test
    public void testChainingUpdate() {
        // 普通链式更新示例
        update(Employee.class).set("last_name", "张三三").eq("email", "3333333333@qq.com").update();

        // lambda 链式更新示例
        Employee employee = lambdaQuery(Employee.class).eq(Employee::getId, 1l).one();
        employee.setLastName("张一一");
        lambdaUpdate(Employee.class).set(Employee::getLastName, employee.getLastName()).eq(Employee::getId, 1l).update();
    }
}
