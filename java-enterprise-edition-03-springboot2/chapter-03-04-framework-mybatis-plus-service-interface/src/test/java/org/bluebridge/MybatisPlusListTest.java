package org.bluebridge;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.bluebridge.entity.Employee;
import org.bluebridge.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * List<T> list();  // 查询所有
 * List<T> list(Wrapper<T> queryWrapper);   // 查询列表
 * Collection<T> listByIds(Collection<? extends Serializable> idList);  // 查询（根据ID 批量查询）
 * Collection<T> listByMap(Map<String, Object> columnMap);  // 查询（根据 columnMap 条件）
 * List<Map<String, Object>> listMaps();    // 查询所有列表
 * List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper); // 查询列表
 * List<Object> listObjs(); // 查询全部记录
 * List<Object> listObjs(Wrapper<T> queryWrapper);  // 根据 Wrapper 条件，查询全部记录
 */
@SpringBootTest
public class MybatisPlusListTest {

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
     * 测试查询所有
     * List<T> list();
     */
    @Test
    public void testList() {
        List<Employee> employeeList = employeeService.list();
        System.out.println("employeeList = " + employeeList);
    }

    /**
     * 测试查询列表
     * List<T> list(Wrapper<T> queryWrapper);
     */
    @Test
    public void testListByQueryWrapper() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gender","男");
        List<Employee> employeeList = employeeService.list(queryWrapper);
        System.out.println("employeeList = " + employeeList);
    }

    /**
     * 测试查询（根据ID 批量查询）
     * List<T> list(Wrapper<T> queryWrapper);
     */
    @Test
    public void testListByIds() {
        List<Long> idList = Arrays.asList(1l, 2l, 3l, 4l);
        List<Employee> employeeList = employeeService.listByIds(idList);
        System.out.println("employeeList = " + employeeList);
    }

    /**
     * 测试查询（根据 columnMap 条件）
     * List<T> list(Wrapper<T> queryWrapper);
     */
    @Test
    public void testListByMap() {
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("gender","男");
        List<Employee> employeeList = employeeService.listByMap(conditionMap);
        System.out.println("employeeList = " + employeeList);
    }

    /**
     * 测试查询所有列表
     * List<Map<String, Object>> listMaps();
     */
    @Test
    public void testListMaps() {
        List<Map<String, Object>> maps = employeeService.listMaps();
        System.out.println("maps = " + maps);
    }

    /**
     * 查询列表
     * List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper);
     */
    @Test
    public void testListMapsByQueryWrapper() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gender","男");
        List<Map<String, Object>> maps = employeeService.listMaps(queryWrapper);
        System.out.println("maps = " + maps);
    }

    /**
     * 测试查询全部记录的id
     * List<Object> listObjs();
     */
    @Test
    public void testListObjs() {
        List<Object> objects = employeeService.listObjs();
        System.out.println("objects = " + objects);
    }

    /**
     * 测试根据 Wrapper 条件，查询全部记录的id
     * List<Object> listObjs(Wrapper<T> queryWrapper);
     */
    @Test
    public void testListObjsByQueryWrapper() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gender","男");
        List<Object> objects = employeeService.listObjs(queryWrapper);
        System.out.println("objects = " + objects);
    }
}
