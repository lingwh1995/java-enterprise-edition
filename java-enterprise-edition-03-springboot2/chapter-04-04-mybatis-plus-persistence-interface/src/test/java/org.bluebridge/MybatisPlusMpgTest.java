package org.bluebridge;

import org.bluebridge.controller.EmployeeController;
import org.bluebridge.entity.Employee;
import org.bluebridge.mapper.EmployeeMapper;
import org.bluebridge.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class MybatisPlusMpgTest {

    @Autowired
    private IEmployeeService employeeService;

    @Test
    public void testSelect() {
//        employeeService.save()
        /*
        //
        boolean save(T entity);
// 插入（批量）
        boolean saveBatch(Collection<T> entityList);
// 插入（批量）
        boolean saveBatch(Collection<T> entityList, int batchSize);
        */
    }

    /**
     * 测试插入一条记录（选择字段，策略插入）
     */
    @Test
    public void testSave() {
        Employee employee = Employee.builder().email("1458687169@qq.com")
                .gender("男")
                .lastName("张三")
                .deptNo("01")
                .build();
        boolean isSave = employeeService.save(employee);
        System.out.println(isSave);
    }

    /**
     * 测试批量插入(不指定批次大小)
     */
    @Test
    public void testSaveBatchWithoutSetBatchSize() {
        Employee zhangsan = Employee.builder().email("1458687169@qq.com")
                .gender("男")
                .lastName("张三")
                .deptNo("01")
                .build();
        Employee lisi = Employee.builder().email("1358697169@qq.com")
                .gender("男")
                .lastName("李四")
                .deptNo("01")
                .build();
        Employee wangwu = Employee.builder().email("1958687169@qq.com")
                .gender("男")
                .lastName("王五")
                .deptNo("02")
                .build();
        Employee zhaoliu = Employee.builder().email("1868687169@qq.com")
                .gender("男")
                .lastName("赵六")
                .deptNo("02")
                .build();
        List<Employee> employeeList = Arrays.asList(zhangsan, lisi, wangwu,zhaoliu);
        boolean isSave = employeeService.saveBatch(employeeList);
        System.out.println(isSave);
    }

    /**
     * 测试批量插入(指定批次大小)
     */
    @Test
    public void testSaveBatchSetBatchSize() {
        Employee zhangsan = Employee.builder().email("1458687169@qq.com")
                .gender("男")
                .lastName("张三")
                .deptNo("01")
                .build();
        Employee lisi = Employee.builder().email("1358697169@qq.com")
                .gender("男")
                .lastName("李四")
                .deptNo("01")
                .build();
        Employee wangwu = Employee.builder().email("1958687169@qq.com")
                .gender("男")
                .lastName("王五")
                .deptNo("02")
                .build();
        Employee zhaoliu = Employee.builder().email("1868687169@qq.com")
                .gender("男")
                .lastName("赵六")
                .deptNo("02")
                .build();
        List<Employee> employeeList = Arrays.asList(zhangsan, lisi, wangwu,zhaoliu);
        //指定批次大小
        boolean isSave = employeeService.saveBatch(employeeList, 2);
        System.out.println(isSave);
    }

}