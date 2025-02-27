package org.bluebridge;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.bluebridge.entity.Employee;
import org.bluebridge.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * boolean update(Wrapper<T> updateWrapper);    // 根据 UpdateWrapper 条件，更新记录 需要设置sqlset
 * boolean update(T updateEntity, Wrapper<T> whereWrapper);  // 根据 whereWrapper 条件，更新记录
 * boolean updateById(T entity);    // 根据 ID 选择修改
 * boolean updateBatchById(Collection<T> entityList);   // 根据ID 批量更新
 * boolean updateBatchById(Collection<T> entityList, int batchSize);    // 根据ID 批量更新
 */
@SpringBootTest
public class MybatisPlusUpdateAndUpdateBatchTest {

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 测试根据 UpdateWrapper 条件，更新记录 需要设置sqlset
     * boolean update(Wrapper<T> updateWrapper);
     */
    @Test
    public void testUpdateByUpdateWrapper() {
        UpdateWrapper<Employee> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("gender", "男").set("last_name", "new name");
        boolean isUpdate = employeeService.update(updateWrapper);
        System.out.println("isUpdate = " + isUpdate);
    }

    /**
     * 测试根据 whereWrapper 条件，更新记录
     * boolean update(T updateEntity, Wrapper<T> whereWrapper);
     */
    @Test
    public void testUpdateByWhereWrapper() {
        Employee employee = Employee.builder().lastName("new name~").build();
        QueryWrapper<Employee> whereWrapper = new QueryWrapper<>();
        whereWrapper.eq("gender","男");
        boolean isUpdate = employeeService.update(employee, whereWrapper);
        System.out.println("isUpdate = " + isUpdate);
    }

    /**
     * 测试根据 ID 选择修改
     * boolean updateById(T entity);
     */
    @Test
    public void testUpdateById() {
        Employee employee = Employee.builder()
                .id(30l)
                .lastName("new name~~")
                .build();
        boolean isUpdateById = employeeService.updateById(employee);
        System.out.println("isUpdateById = " + isUpdateById);
    }

    /**
     * 测试根据ID 批量更新
     * boolean updateBatchById(Collection<T> entityList);
     */
    @Test
    public void testUpdateByIdBatchWithOutBatchSize() {
//        List<Employee> employeeList = Arrays.asList(
//                new Employee(1, null, "new.email1@example.com"),
//                new Employee(2, null, "new.email2@example.com")
//        );
    }

    /**
     * 测试根据ID 批量更新
     * boolean updateBatchById(Collection<T> entityList, int batchSize);
     */
    @Test
    public void testUpdateByIdBatchWithBatchSize() {

    }
}
