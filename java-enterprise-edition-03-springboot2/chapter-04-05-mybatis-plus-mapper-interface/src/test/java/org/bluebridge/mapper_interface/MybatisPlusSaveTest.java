package org.bluebridge.mapper_interface;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
     * 首先执行这个方法初始化数据库数据
     */
    @Test
    public void init(){
        //删除数据库中t_employee表中所有数据
        boolean isRemove = employeeService.remove(new QueryWrapper<>());
        System.out.println("isRemove = " + isRemove);
    }

    /**
     * 测试插入一条记录（选择字段，策略插入）
     */
    @Test
    public void testSave() {
        Employee employee = Employee.builder()
                .id(1l)
                .email("1111111111@qq.com")
                .gender("男")
                .lastName("张一")
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
                new Employee(2l,"张二", "2222222222@qq.com", "男", "01"),
                new Employee(3l,"张三", "3333333333@qq.com", "女", "02"),
                new Employee(4l,"张四", "44444444444@qq.com", "男", "03"),
                new Employee(5l,"张五", "55555555555@qq.com", "男", "03")
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
                new Employee(6l,"张六", "6666666666@qq.com", "男", "01"),
                new Employee(7l,"张七", "7777777777@qq.com", "女", "02"),
                new Employee(8l,"张八", "8888888888@qq.com", "男", "03"),
                new Employee(9l,"张九", "9999999999@qq.com", "男", "03")
        );
        //指定批次大小
        boolean isSave = employeeService.saveBatch(employeeList,2);
        System.out.println(isSave);
    }
}