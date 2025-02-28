package org.bluebridge.service_interface;

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
                new Employee(4l,"张四", "4444444444@qq.com", "男", "03"),
                new Employee(5l,"张五", "5555555555@qq.com", "男", "03"),
                new Employee(6l,"张六", "6666666666@qq.com", "男", "03"),
                new Employee(7l,"张七", "7777777777@qq.com", "男", "03"),
                new Employee(8l,"张八", "8888888888@qq.com", "男", "03"),
                new Employee(9l,"张九", "9999999999@qq.com", "男", "03"),
                new Employee(10l,"张A", "aaaaaaaaaa@qq.com", "男", "03")
        );
        //给数据库中插入测试数据
        boolean isSaveBatch = employeeService.saveBatch(employeeList);
        System.out.println("isSaveBatch = " + isSaveBatch);
    }

    /**
     * 测试根据 queryWrapper 设置的条件，删除记录
     * boolean remove(Wrapper<T> queryWrapper);
     */
    @Test
    public void testRemoveByQueryWrapper() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("last_name", "张一").or().eq("email", "2222222222@qq.com");
        boolean isRemove = employeeService.remove(queryWrapper);
        System.out.println("isRemove = " + isRemove);
    }

    /**
     * 测试根据 ID 删除
     * boolean removeById(Serializable id);
     */
    @Test
    public void testRemoveById() {
        boolean isRemoveById = employeeService.removeById(3l);
        System.out.println("isRemoveById = " + isRemoveById);
    }

    /**
     * 测试根据 columnMap 条件，删除记录
     * boolean removeByMap(Map<String, Object> columnMap);
     */
    @Test
    public void testRemoveByMap() {
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("email", "4444444444@qq.com");
        boolean isRemoveByMap = employeeService.removeByMap(conditionMap);
        System.out.println("isRemoveByMap = " + isRemoveByMap);
    }

    /**
     * 测试删除（根据ID 批量删除）
     * boolean removeByIds(Collection<? extends Serializable> idList);
     */
    @Test
    public void testRemoveByIds() {
        List<Long> idList = Arrays.asList(5l, 6l);
        boolean isRemoveByIds = employeeService.removeByIds(idList);
        System.out.println("isRemoveByIds = " + isRemoveByIds);
    }

    /**
     * 测试删除（根据ID 批量删除）
     * boolean removeByIds(Collection<? extends Serializable> idList);
     */
    @Test
    public void testRemoveByIdsWithoutBatchSize() {
        List<Long> idList = Arrays.asList(7l, 8l);
        boolean isRemoveBatchByIds = employeeService.removeBatchByIds(idList);
        System.out.println("isRemoveBatchByIds = " + isRemoveBatchByIds);
    }

    /**
     * 测试删除（根据ID 批量删除）
     * boolean removeByIds(Collection<? extends Serializable> idList);
     */
    @Test
    public void testRemoveByIdsWithBatchSize() {
        List<Long> idList = Arrays.asList(9l, 10l);
        //设置batchSize为2
        boolean isRemoveBatchByIds = employeeService.removeBatchByIds(idList,2);
        System.out.println("isRemoveBatchByIds = " + isRemoveBatchByIds);
    }
}
