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
 * boolean remove(Wrapper<T> queryWrapper);  // 根据 queryWrapper 设置的条件，删除记录
 * boolean removeById(Serializable id);  // 根据 ID 删除
 * boolean removeByMap(Map<String, Object> columnMap);  // 根据 columnMap 条件，删除记录
 * boolean removeByIds(Collection<? extends Serializable> idList);  // 删除（根据ID 批量删除）
 */
@SpringBootTest
public class MybatisPlusRemoveTest {

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 测试根据 queryWrapper 设置的条件，删除记录
     * boolean remove(Wrapper<T> queryWrapper);
     */
    @Test
    public void testRemoveByQueryWrapper() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("last_name", "张三").or().eq("dept_no", "02");
        boolean isRemove = employeeService.remove(queryWrapper);
        System.out.println("isRemove = " + isRemove);
    }

    /**
     * 测试根据 ID 删除
     * boolean removeById(Serializable id);
     */
    @Test
    public void testRemoveById() {
        boolean isRemoveById = employeeService.removeById(23l);
        System.out.println("isRemoveById = " + isRemoveById);
    }

    /**
     * 测试根据 columnMap 条件，删除记录
     * boolean removeByMap(Map<String, Object> columnMap);
     */
    @Test
    public void testRemoveByMap() {
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("dept_no", "02");
        boolean isRemoveByMap = employeeService.removeByMap(conditionMap);
        System.out.println("isRemoveByMap = " + isRemoveByMap);
    }

    /**
     * 测试删除（根据ID 批量删除）
     * boolean removeByIds(Collection<? extends Serializable> idList);
     */
    @Test
    public void testRemoveByIds() {
        List<Long> idList = Arrays.asList(26l, 27l);
        boolean isRemoveByIds = employeeService.removeByIds(idList);
        System.out.println("isRemoveByIds = " + isRemoveByIds);
    }

    /**
     * 测试删除（根据ID 批量删除）
     * boolean removeByIds(Collection<? extends Serializable> idList);
     */
    @Test
    public void testRemoveByIdsWithoutBatchSize() {
        List<Long> idList = Arrays.asList(26l, 27l);
        boolean isRemoveBatchByIds = employeeService.removeBatchByIds(idList);
        System.out.println("isRemoveBatchByIds = " + isRemoveBatchByIds);
    }

    /**
     * 测试删除（根据ID 批量删除）
     * boolean removeByIds(Collection<? extends Serializable> idList);
     */
    @Test
    public void testRemoveByIdsWithBatchSize() {
        List<Long> idList = Arrays.asList(26l, 27l);
        //设置batchSize为2
        boolean isRemoveBatchByIds = employeeService.removeBatchByIds(idList,2);
        System.out.println("isRemoveBatchByIds = " + isRemoveBatchByIds);
    }
}
