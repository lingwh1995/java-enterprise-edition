package org.bluebridge;

import org.bluebridge.entity.Employee;
import org.bluebridge.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * boolean save(T entity);  // 插入一条记录（选择字段，策略插入）
 * boolean saveBatch(Collection<T> entityList); // 插入（批量）
 * boolean saveBatch(Collection<T> entityList, int batchSize); // 插入（批量）
 */
@SpringBootTest
public class MybatisPlusSaveTest {

    @Autowired
    private IEmployeeService employeeService;

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
     * boolean saveBatch(Collection<T> entityList);
     */
    @Test
    public void testSaveBatchWithoutSetBatchSize() {
        List<Employee> employeeList = Arrays.asList(
                new Employee(null,"张三", "1111111111@qq.com", "男", "01"),
                new Employee(null,"李四", "2222222222@qq.com", "女", "02"),
                new Employee(null,"王五", "3333333333@qq.com", "男", "03"),
                new Employee(null,"赵六", "44444444444@qq.com", "男", "03")
                );
        boolean isSave = employeeService.saveBatch(employeeList);
        System.out.println(isSave);
    }

    /**
     * 测试批量插入(指定批次大小)
     * boolean saveBatch(Collection<T> entityList, int batchSize);
     */
    @Test
    public void testSaveBatchSetBatchSize() {
        List<Employee> employeeList = Arrays.asList(
                new Employee(null,"张三", "1111111111@qq.com", "男", "01"),
                new Employee(null,"李四", "2222222222@qq.com", "女", "02"),
                new Employee(null,"王五", "3333333333@qq.com", "男", "03"),
                new Employee(null,"赵六", "44444444444@qq.com", "男", "03")
        );
        //指定批次大小
        boolean isSave = employeeService.saveBatch(employeeList,2);
        System.out.println(isSave);
    }
}