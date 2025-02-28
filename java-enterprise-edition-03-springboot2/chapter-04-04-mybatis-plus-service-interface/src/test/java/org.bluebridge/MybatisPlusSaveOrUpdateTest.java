package org.bluebridge;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.bluebridge.entity.Employee;
import org.bluebridge.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 *  boolean saveOrUpdate(T entity);  // TableId 注解属性值存在则更新记录，否插入一条记录
 *  boolean saveOrUpdate(T entity, Wrapper<T> updateWrapper);  // 根据updateWrapper尝试更新，否继续执行saveOrUpdate(T)方法
 *  boolean saveOrUpdateBatch(Collection<T> entityList); // 批量修改插入
 *  boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);  // 批量修改插入
 */
public class MybatisPlusSaveOrUpdateTest {

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 测试TableId 注解属性值存在则更新记录，否插入一条记录
     * boolean saveOrUpdate(T entity);
     */
    @Test
    public void testSaveOrUpdate() {
        Employee zhangsan = Employee.builder().email("1458687169@qq.com")
                .id(5l)
                .gender("男")
                .lastName("张三三")
                .deptNo("01")
                .build();
        boolean isSaveOrUpdate = employeeService.saveOrUpdate(zhangsan);
        System.out.println("isSaveOrUpdate = " + isSaveOrUpdate);
    }

    /**
     * 测试根据updateWrapper尝试更新，否继续执行saveOrUpdate(T)方法
     * boolean saveOrUpdate(T entity, Wrapper<T> updateWrapper);
     */
    @Test
    public void testSaveOrUpdateWithUpdateWrapper() {
        Employee zhangsan = Employee.builder().email("1458687169@qq.com")
                .gender("男")
                .lastName("张四四")
                .deptNo("01")
                .build();
        UpdateWrapper<Employee> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",5l);
        boolean isSaveOrUpdate = employeeService.saveOrUpdate(zhangsan,updateWrapper);
        System.out.println("isSaveOrUpdate = " + isSaveOrUpdate);
    }

    /**
     * 测试批量修改插入(不指定批次大小)
     * boolean saveOrUpdateBatch(Collection<T> entityList);
     */
    @Test
    public void testSaveOrUpdateBatchWithoutSetBatchSize() {
        List<Employee> employeeList = Arrays.asList(
                new Employee(5l,"张三", "1111111111@qq.com", "男", "01"),
                new Employee(null,"李四", "2222222222@qq.com", "女", "02"),
                new Employee(null,"王五", "3333333333@qq.com", "男", "03"),
                new Employee(null,"赵六", "44444444444@qq.com", "男", "03")
        );
        boolean isSave = employeeService.saveOrUpdateBatch(employeeList);
        System.out.println(isSave);
    }

    /**
     * 测试批量修改插入(指定批次大小)
     * boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);
     */
    @Test
    public void testSaveOrUpdateBatchWithSetBatchSize() {
        List<Employee> employeeList = Arrays.asList(
                new Employee(5l,"张三", "1111111111@qq.com", "男", "01"),
                new Employee(null,"李四", "2222222222@qq.com", "女", "02"),
                new Employee(null,"王五", "3333333333@qq.com", "男", "03"),
                new Employee(null,"赵六", "44444444444@qq.com", "男", "03")
        );
        //设置批次大小为2
        boolean isSave = employeeService.saveOrUpdateBatch(employeeList,2);
        System.out.println(isSave);
    }
}
