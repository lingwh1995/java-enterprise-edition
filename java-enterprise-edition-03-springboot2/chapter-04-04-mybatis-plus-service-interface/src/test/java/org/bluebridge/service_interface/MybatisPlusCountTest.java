package org.bluebridge.service_interface;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.bluebridge.entity.Employee;
import org.bluebridge.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * //自3.4.3.2开始,返回值修改为long
 * long count();    // 查询总记录数
 * long count(Wrapper<T> queryWrapper); // 根据 Wrapper 条件，查询总记录数
 */
@SpringBootTest
public class MybatisPlusCountTest {

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
                new Employee(4l,"张四", "44444444444@qq.com", "男", "03")
        );
        //给数据库中插入测试数据
        boolean isSaveBatch = employeeService.saveBatch(employeeList);
        System.out.println("isSaveBatch = " + isSaveBatch);
    }

    /**
     * 测试查询总记录数
     */
    @Test
    public void testCount() {
        long count = employeeService.count();
        System.out.println("count = " + count);
    }

    /**
     * 测试根据条件查询总记录数
     */
    @Test
    public void testCountByWrapper() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gender","男");
        long count = employeeService.count(queryWrapper);
        System.out.println("count = " + count);
    }

}
