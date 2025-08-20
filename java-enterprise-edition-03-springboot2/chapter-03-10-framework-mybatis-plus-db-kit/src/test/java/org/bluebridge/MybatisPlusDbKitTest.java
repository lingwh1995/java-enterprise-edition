package org.bluebridge;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.bluebridge.entity.Employee;
import org.bluebridge.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * 注意：  DbKit相关api需要写mapper，否则会报错
 */
@SpringBootTest
public class MybatisPlusDbKitTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    public void init() {
        //删除数据库中数据
        employeeMapper.delete(new QueryWrapper<>());
        //给数据库中插入数据
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

    /**
     * 测试 根据 id 查询单个实体
     */
    @Test
    public void testGetById() {
        Employee employee = Db.getById(1l, Employee.class);
        System.out.println("employee = " + employee);
    }

    /**
     * 测试 根据 id 查询多个实体
     */
    @Test
    public void testListByIds() {
        List<Long> ids = Arrays.asList(1l, 2l, 3l);
        List<Employee> employeeList = Db.listByIds(ids, Employee.class);
        System.out.println("employeeList = " + employeeList);
    }

    /**
     * 测试 根据QueryWrapper封装的条件查询
     */
    @Test
    public void testListByQueryWrapper() {
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery(Employee.class)
                .eq(Employee::getGender, "男");
        List<Employee> employeeList = Db.list(queryWrapper);
        System.out.println("employeeList = " + employeeList);
    }

    /**
     * 测试 插入新实体
     */
    @Test
    public void testSave() {
        Employee employee = Employee.builder()
                .id(7l)
                .email("7777777777@qq.com")
                .gender("男")
                .lastName("张七")
                .deptNo("07")
                .build();
        boolean isSave = Db.save(employee);
        System.out.println("isSave = " + isSave);
    }

    /**
     * 测试 根据 id 更新实体
     */
    @Test
    public void testUpdateById() {
        Employee employee = Employee.builder()
                .id(1l)
                .email("1111111111@qq.com")
                .gender("男")
                .lastName("张一一")
                .deptNo("01")
                .build();
        boolean isUpdated = Db.updateById(employee);
        System.out.println("isUpdated = " + isUpdated);
    }

    /**
     * 测试 根据UpdateWrapper更新Employee
     */
    @Test
    public void testUpdateByUpdateWrapper() {
        // 根据条件构造器更新
        LambdaUpdateWrapper<Employee> updateWrapper = Wrappers.lambdaUpdate(Employee.class)
                .set(Employee::getLastName, "张二二")
                .eq(Employee::getId, 02l);
        boolean isUpdatedByWrapper = Db.update(null, updateWrapper);
        System.out.println("isUpdatedByWrapper = " + isUpdatedByWrapper);
    }

    /**
     * 测试根据 id 删除
     */
    @Test
    public void testRemoveById() {
        boolean isRemoved = Db.removeById(6l,Employee.class);
        System.out.println("isRemoved = " + isRemoved);
    }
    /**
     * 测试根据 LambdaDeleteWrapper(根据条件构造器删除) 删除
     */
    @Test
    public void testRemoveByLambdaDeleteWrapper() {
        LambdaUpdateWrapper<Employee> deleteWrapper = Wrappers.lambdaUpdate(Employee.class)
                .eq(Employee::getLastName, "张五");
        boolean isDeletedByWrapper = Db.remove(deleteWrapper);
    }

    /**
     * 批量插入
     */
    @Test
    public void testSaveBatch() {
        Employee employee08 = Employee.builder()
                .id(8l)
                .email("8888888888@qq.com")
                .gender("男")
                .lastName("张八")
                .deptNo("08")
                .build();
        Employee employee09 = Employee.builder()
                .id(9l)
                .email("9999999999@qq.com")
                .gender("男")
                .lastName("张九")
                .deptNo("09")
                .build();
        List<Employee> employeeList = Arrays.asList(employee08, employee09);
        boolean isSaveBatch = Db.saveBatch(employeeList);
        System.out.println("isSaveBatch = " + isSaveBatch);
    }

    /**
     * 批量更新
     */
    @Test
    public void testUpdateBatch() {
        Employee employee08 = Employee.builder()
                .id(8l)
                .email("8888888888@qq.com")
                .gender("男")
                .lastName("张八八")
                .deptNo("08")
                .build();
        Employee employee09 = Employee.builder()
                .id(9l)
                .email("9999999999@qq.com")
                .gender("男")
                .lastName("张九九")
                .deptNo("09")
                .build();
        List<Employee> employeeList = Arrays.asList(employee08, employee09);
        boolean isUpdateBatchById = Db.updateBatchById(employeeList);
        System.out.println("isUpdateBatchById = " + isUpdateBatchById);
    }
}
