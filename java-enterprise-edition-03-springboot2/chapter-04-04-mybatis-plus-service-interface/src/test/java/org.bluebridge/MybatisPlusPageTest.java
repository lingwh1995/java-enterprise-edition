package org.bluebridge;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.bluebridge.entity.Employee;
import org.bluebridge.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * IPage<T> page(IPage<T> page);    // 无条件分页查询
 * IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper);   // 条件分页查询
 * IPage<Map<String, Object>> pageMaps(IPage<T> page);  // 无条件分页查询
 * IPage<Map<String, Object>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper);   // 条件分页查询
 */
@SpringBootTest
public class MybatisPlusPageTest {

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 首先执行这个方法为数据库插入初始数据
     */
    @Test
    public void init(){
        List<Employee> employeeList = Arrays.asList(
                new Employee(null,"张三", "1111111111@qq.com", "男", "01"),
                new Employee(null,"李四", "2222222222@qq.com", "女", "02"),
                new Employee(null,"王五", "3333333333@qq.com", "男", "03"),
                new Employee(null,"赵六", "44444444444@qq.com", "男", "03")
        );
        boolean isSave = employeeService.saveBatch(employeeList);
    }

    /**
     * 测试无条件分页查询
     *      IPage<T> page(IPage<T> page);
     */
    @Test
    public void testPage() {
        IPage<Employee> page = new Page<>(1, 10);
        IPage<Employee> userPage = employeeService.page(page);
        List<Employee> records = userPage.getRecords();
        long total = userPage.getTotal();
        System.out.println("records = " + records);
        System.out.println("total = " + total);
    }

    /**
     * 测试条件分页查询
     *      IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper);
     */
    @Test
    public void testPageByQueryWrapper() {
        IPage<Employee> page = new Page<>(1, 10);
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gender","男");
        IPage<Employee> userPage = employeeService.page(page,queryWrapper);
        List<Employee> records = userPage.getRecords();
        long total = userPage.getTotal();
        System.out.println("records = " + records);
        System.out.println("total = " + total);
    }

    /**
     * 测试无条件分页查询
     *      IPage<Map<String, Object>> pageMaps(IPage<T> page);
     */
    @Test
    public void testPageMaps() {
        IPage<Map<String, Object>> page = new Page<>(1, 10);
        IPage<Map<String, Object>> mapIPage = employeeService.pageMaps(page);
        List<Map<String, Object>> records = mapIPage.getRecords();
        long total = mapIPage.getTotal();
        System.out.println("records = " + records);
        System.out.println("total = " + total);
    }

    /**
     * 测试条件分页查询
     *      IPage<Map<String, Object>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper);
     */
    @Test
    public void testPageMapsByQueryWrapper() {
        IPage<Map<String, Object>> page = new Page<>(1, 10);
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gender","男");
        IPage<Map<String, Object>> mapIPage = employeeService.pageMaps(page,queryWrapper);
        List<Map<String, Object>> records = mapIPage.getRecords();
        long total = mapIPage.getTotal();
        System.out.println("records = " + records);
        System.out.println("total = " + total);
    }
}
