package org.bluebridge.mapper_interface;

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
 * 注意：
 *      需要MybatisPlusConfig在中配置分页插件，否则page方法查询返回的total值为0
 *
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
