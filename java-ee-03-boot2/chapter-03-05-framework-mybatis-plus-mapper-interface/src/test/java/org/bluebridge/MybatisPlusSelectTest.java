package org.bluebridge;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * T selectById(Serializable id);   // 根据 ID 查询
 * T selectOne(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);  // 根据 entity 条件，查询一条记录
 * List<T> selectBatchIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);  // 查询（根据ID 批量查询）
 * List<T> selectList(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);   // 根据 entity 条件，查询全部记录
 * List<T> selectByMap(@Param(Constants.COLUMN_MAP) Map<String, Object> columnMap);     // 查询（根据 columnMap 条件）
 * List<Map<String, Object>> selectMaps(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);     // 根据 Wrapper 条件，查询全部记录
 * List<Object> selectObjs(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);  // 根据 Wrapper 条件，查询全部记录。注意： 只返回第一个字段的值
 * IPage<T> selectPage(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);   // 根据 entity 条件，查询全部记录（并翻页）
 * IPage<Map<String, Object>> selectMapsPage(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);     // 根据 Wrapper 条件，查询全部记录（并翻页）
 * Integer selectCount(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);  // 根据 Wrapper 条件，查询总记录数
 */
@SpringBootTest
public class MybatisPlusSelectTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    public void init() {
        //删除数据库中已有数据
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
     * 测试 根据 ID 查询
     * T selectById(Serializable id);
     */
    @Test
    public void testSelectById() {
        Employee employee = employeeMapper.selectById(1l);
        System.out.println("employee = " + employee);
    }

    /**
     * 测试 根据 entity 条件，查询一条记录
     * T selectOne(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
     */
    @Test
    public void testSelectOne() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("last_name","张二");
        Employee employee = employeeMapper.selectOne(queryWrapper);
        System.out.println("employee = " + employee);
    }

    /**
     * 测试 查询（根据ID 批量查询）
     * List<T> selectBatchIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);
     */
    @Test
    public void testSelectBatchIds() {
        List<Long> ids = Arrays.asList(1l, 2l, 3l, 4l, 5l, 6l);
        List<Employee> employeeList = employeeMapper.selectBatchIds(ids);
        System.out.println("employeeList = " + employeeList);
    }

    /**
     * 测试 根据 entity 条件，查询全部记录
     * List<T> selectList(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
     */
    @Test
    public void testSelectList() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gender","男");
        List<Employee> employeeList = employeeMapper.selectList(queryWrapper);
        System.out.println("employeeList = " + employeeList);
    }

    /**
     * 测试 查询（根据 columnMap 条件）
     * List<T> selectByMap(@Param(Constants.COLUMN_MAP) Map<String, Object> columnMap);
     */
    @Test
    public void testSelectByMap() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("gender","男");
        List<Employee> employeeList = employeeMapper.selectByMap(columnMap);
        System.out.println("employeeList = " + employeeList);
    }

    /**
     * 测试 根据 Wrapper 条件，查询全部记录
     * List<Map<String, Object>> selectMaps(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
     */
    @Test
    public void testSelectMaps() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gender","男");
        List<Map<String, Object>> maps = employeeMapper.selectMaps(queryWrapper);
        System.out.println("maps = " + maps);
    }

    /**
     * 测试 根据 Wrapper 条件，查询全部记录。注意： 只返回第一个字段的值,这里返回的值实际上是id
     * List<Object> selectObjs(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
     */
    @Test
    public void testSelectObjs() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gender","男");
        List<Object> objects = employeeMapper.selectObjs(queryWrapper);
        System.out.println("objects = " + objects);
    }

    /**
     * 测试 根据 entity 条件，查询全部记录（并翻页）
     * IPage<T> selectPage(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
     */
    @Test
    public void testSelectPage() {
        IPage<Employee> page = new Page<>(1, 10);
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gender","男");
        IPage<Employee> employeeIPage = employeeMapper.selectPage(page, queryWrapper);
        long total = employeeIPage.getTotal();
        List<Employee> records = employeeIPage.getRecords();
        System.out.println("total = " + total);
        System.out.println("records = " + records);
    }

    /**
     * 测试 根据 Wrapper 条件，查询全部记录（并翻页）
     * IPage<Map<String, Object>> selectMapsPage(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
     */
    @Test
    public void testSelectMapsPage() {
        IPage<Map<String, Object>> page = new Page<>(1, 10);
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gender","男");
        IPage<Map<String, Object>> mapIPage = employeeMapper.selectMapsPage(page, queryWrapper);
        long total = mapIPage.getTotal();
        List<Map<String, Object>> records = mapIPage.getRecords();
        System.out.println("total = " + total);
        System.out.println("records = " + records);
        System.out.println("records.get(0) = " + records.get(0));
    }

    /**
     * 测试 根据 Wrapper 条件，查询总记录数
     * Integer selectCount(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
     */
    @Test
    public void testSelectCount() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gender","男");
        Long count = employeeMapper.selectCount(queryWrapper);
        System.out.println("count = " + count);
    }
}
