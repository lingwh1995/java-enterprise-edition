package org.bluebridge;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
 * int delete(@Param(Constants.WRAPPER) Wrapper<T> wrapper);    // 根据 entity 条件，删除记录
 * int deleteBatchIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);  // 删除（根据ID 批量删除）
 * int deleteById(Serializable id);     // 根据 ID 删除
 * int deleteByMap(@Param(Constants.COLUMN_MAP) Map<String, Object> columnMap);     // 根据 columnMap 条件，删除记录
 */
@SpringBootTest
public class MybatisPlusDeleteTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    public void init() {
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
     * 测试根据 entity 条件，删除记录
     * int delete(@Param(Constants.WRAPPER) Wrapper<T> wrapper);
     */
    @Test
    public void testDelete() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("last_name","张一");
        int delete = employeeMapper.delete(queryWrapper);
        System.out.println("delete = " + delete);
    }

    /**
     * 测试 删除（根据ID 批量删除）
     * int deleteBatchIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);
     */
    @Test
    public void testDeleteBatchIds() {
        List<Long> idList = Arrays.asList(2l,3l);
        int deleteBatchIds = employeeMapper.deleteBatchIds(idList);
        System.out.println("deleteBatchIds = " + deleteBatchIds);
    }

    /**
     * 测试根据 ID 删除
     * int deleteById(Serializable id);
     */
    @Test
    public void testDeleteById() {
        int i = employeeMapper.deleteById(4l);
        System.out.println("i = " + i);
    }

    /**
     * 测试根据 Entity 删除
     * int deleteById(Serializable id);
     */
    @Test
    public void testDeleteByEntity() {
        Employee employee = employeeMapper.selectById(5l);
        int i = employeeMapper.deleteById(employee);
        System.out.println("i = " + i);
    }

    /**
     * 测试 根据 columnMap 条件，删除记录
     * int deleteByMap(@Param(Constants.COLUMN_MAP) Map<String, Object> columnMap);
     */
    @Test
    public void testDeleteByColumnMap() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("last_name","张六");
        int i = employeeMapper.deleteByMap(columnMap);
        System.out.println("i = " + i);
    }
}
