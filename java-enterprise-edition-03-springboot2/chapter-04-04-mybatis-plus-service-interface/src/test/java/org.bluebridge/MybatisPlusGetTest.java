package org.bluebridge;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.bluebridge.entity.Employee;
import org.bluebridge.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * T getById(Serializable id);  // 根据 ID 查询
 * T getOne(Wrapper<T> queryWrapper);   // 根据 Wrapper，查询一条记录。结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")
 * T getOne(Wrapper<T> queryWrapper, boolean throwEx);  // 根据 Wrapper，查询一条记录
 * Map<String, Object> getMap(Wrapper<T> queryWrapper); // 根据 Wrapper，查询一条记录
 * <V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper);   // 根据 Wrapper，查询一条记录
 */
@SpringBootTest
public class MybatisPlusGetTest {

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
     * 测试根据ID查询
     * T getById(Serializable id);
     */
    @Test
    public void testGetById() {
        Employee employee = employeeService.getById(1l);
        System.out.println("employee = " + employee);
    }

    /**
     * 测试根据 Wrapper，查询一条记录。结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")
     * T getOne(Wrapper<T> queryWrapper);
     */
    @Test
    public void testGetOneThrowException() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("last_name","张一");
        Employee employee = employeeService.getOne(queryWrapper);
        System.out.println("employee = " + employee);
    }

    /**
     * 测试根据 Wrapper，查询一条记录
     * T getOne(Wrapper<T> queryWrapper, boolean throwEx);
     */
    @Test
    public void testGetOneNotThrowException() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("last_name","张一");
        Employee employee = employeeService.getOne(queryWrapper,false);
        System.out.println("employee = " + employee);
    }

    /**
     * 测试根据 Wrapper，查询一条记录
     * Map<String, Object> getMap(Wrapper<T> queryWrapper);
     */
    @Test
    public void testGetMap() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("last_name","张一");
        Map<String, Object> map = employeeService.getMap(queryWrapper);
        System.out.println("map = " + map);
    }
}
