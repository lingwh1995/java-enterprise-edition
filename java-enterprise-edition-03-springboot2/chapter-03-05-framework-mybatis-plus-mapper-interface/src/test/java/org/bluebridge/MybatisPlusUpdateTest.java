package org.bluebridge;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.bluebridge.entity.Employee;
import org.bluebridge.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * int update(@Param(Constants.ENTITY) T updateEntity, @Param(Constants.WRAPPER) Wrapper<T> whereWrapper);  // 根据 whereWrapper 条件，更新记录
 * int updateById(@Param(Constants.ENTITY) T entity);   // 根据 ID 修改
 */
@SpringBootTest
public class MybatisPlusUpdateTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    public void init() {
        List<Employee> employeeList = Arrays.asList(
                new Employee(1l,"张一", "1111111111@qq.com", "男", "01"),
                new Employee(2l,"张二", "2222222222@qq.com", "女", "02")
        );
        employeeList.forEach(employee -> {
            employeeMapper.insert(employee);
        });
    }

    /**
     * 测试 根据 whereWrapper 条件，更新记录
     * int update(@Param(Constants.ENTITY) T updateEntity, @Param(Constants.WRAPPER) Wrapper<T> whereWrapper);
     */
    @Test
    public void testUpdateByUpdateWrapper() {
        UpdateWrapper<Employee> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("last_name", "张一");
        Employee employee = Employee.builder()
                .email("1111111111@qq.com")
                .gender("男")
                .lastName("张一一")
                .deptNo("01")
                .build();
        int update = employeeMapper.update(employee, updateWrapper);
        System.out.println("update = " + update);
    }

    /**
     * 测试 根据 ID 修改
     * int update(@Param(Constants.ENTITY) T updateEntity, @Param(Constants.WRAPPER) Wrapper<T> whereWrapper);
     */
    @Test
    public void testUpdateById() {
        Employee employee = employeeMapper.selectById(2l);
        employee.setLastName("张二二");
        int i = employeeMapper.updateById(employee);
        System.out.println("i = " + i);
    }

}
