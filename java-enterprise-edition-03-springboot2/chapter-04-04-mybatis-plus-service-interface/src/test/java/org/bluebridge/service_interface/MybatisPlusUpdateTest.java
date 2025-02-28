package org.bluebridge.service_interface;

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
public class MybatisPlusUpdateTest {

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

        List<Employee> employeeList = Arrays.asList(
                new Employee(1l,"张一", "1111111111@qq.com", "男", "01"),
                new Employee(2l,"张二", "2222222222@qq.com", "女", "02"),
                new Employee(3l,"张三", "3333333333@qq.com", "男", "03"),
                new Employee(4l,"张四", "44444444444@qq.com", "男", "03"),
                new Employee(5l,"张五", "55555555555@qq.com", "男", "03"),
                new Employee(6l,"张六", "55555555555@qq.com", "男", "03"),
                new Employee(7l,"张七", "55555555555@qq.com", "男", "03"),
                new Employee(8l,"张八", "55555555555@qq.com", "男", "03"),
                new Employee(9l,"张九", "55555555555@qq.com", "男", "03")
        );
        //给数据库中插入测试数据
        boolean isSaveBatch = employeeService.saveBatch(employeeList);
        System.out.println("isSaveBatch = " + isSaveBatch);
    }

    /**
     * 测试根据 UpdateWrapper 条件，更新记录 需要设置sqlset
     * boolean update(Wrapper<T> updateWrapper);
     */
    @Test
    public void testUpdateByUpdateWrapper() {
        UpdateWrapper<Employee> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", 1l);
        updateWrapper.set("last_name", "张一一");
        boolean isUpdate = employeeService.update(updateWrapper);
        System.out.println("isUpdate = " + isUpdate);
    }

    /**
     * 测试根据 whereWrapper 条件，更新记录
     * boolean update(T updateEntity, Wrapper<T> whereWrapper);
     */
    @Test
    public void testUpdateByWhereWrapper() {
        Employee employee = Employee.builder().lastName("张二二").build();
        QueryWrapper<Employee> whereWrapper = new QueryWrapper<>();
        whereWrapper.eq("id",2l);
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
                .id(3l)
                .lastName("张三三")
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
        List<Employee> employeeList = Arrays.asList(
                new Employee(4l,"张四四", "44444444444@qq.com", "男", "03"),
                new Employee(5l,"张五五", "55555555555@qq.com", "男", "03")
        );
        boolean isUpdateBatchById = employeeService.updateBatchById(employeeList);
        System.out.println("isUpdateBatchById = " + isUpdateBatchById);
    }

    /**
     * 测试根据ID 批量更新
     * boolean updateBatchById(Collection<T> entityList, int batchSize);
     */
    @Test
    public void testUpdateByIdBatchWithBatchSize() {
        List<Employee> employeeList = Arrays.asList(
                new Employee(6l,"张六六", "66666666666@qq.com", "男", "03"),
                new Employee(7l,"张七七", "66666666666@qq.com", "男", "03"),
                new Employee(8l,"张八八", "44444444444@qq.com", "男", "03"),
                new Employee(9l,"张九九", "55555555555@qq.com", "男", "03")
        );
        boolean isUpdateBatchById = employeeService.updateBatchById(employeeList, 2);
        System.out.println("isUpdateBatchById = " + isUpdateBatchById);
    }
}
