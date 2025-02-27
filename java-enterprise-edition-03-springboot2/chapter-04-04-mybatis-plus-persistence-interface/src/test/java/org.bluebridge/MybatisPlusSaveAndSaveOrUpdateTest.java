package org.bluebridge;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.bluebridge.entity.Employee;
import org.bluebridge.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * boolean save(T entity);  // 插入一条记录（选择字段，策略插入）
 * boolean saveBatch(Collection<T> entityList); // 插入（批量）
 * boolean saveBatch(Collection<T> entityList, int batchSize); // 插入（批量）
 * boolean saveOrUpdate(T entity);  // TableId 注解属性值存在则更新记录，否插入一条记录
 * boolean saveOrUpdate(T entity, Wrapper<T> updateWrapper);  // 根据updateWrapper尝试更新，否继续执行saveOrUpdate(T)方法
 * boolean saveOrUpdateBatch(Collection<T> entityList); // 批量修改插入
 * boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);  // 批量修改插入
 */
@SpringBootTest
public class MybatisPlusSaveAndSaveOrUpdateTest {

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 测试插入一条记录（选择字段，策略插入）
     */
    @Test
    public void testSave() {
        Employee employee = Employee.builder().email("1458687169@qq.com")
                .gender("男")
                .lastName("张三")
                .deptNo("01")
                .build();
        boolean isSave = employeeService.save(employee);
        System.out.println(isSave);
    }

    /**
     * 测试批量插入(不指定批次大小)
     * boolean saveBatch(Collection<T> entityList);
     */
    @Test
    public void testSaveBatchWithoutSetBatchSize() {
        List<Employee> employeeList = Arrays.asList(
                new Employee("张三", "1111111111@qq.com", "男", "01"),
                new Employee("李四", "2222222222@qq.com", "女", "02"),
                new Employee("王五", "3333333333@qq.com", "男", "03"),
                new Employee("王五", "44444444444@qq.com", "男", "03")
                );
//        Employee zhangsan = Employee.builder().email()
//                .gender("男")
//                .lastName()
//                .deptNo("01")
//                .build();
//        Employee lisi = Employee.builder().email("1358697169@qq.com")
//                .gender("男")
//                .lastName("李四")
//                .deptNo("01")
//                .build();
//        Employee wangwu = Employee.builder().email("1958687169@qq.com")
//                .gender("男")
//                .lastName("王五")
//                .deptNo("02")
//                .build();
//        Employee zhaoliu = Employee.builder().email("1868687169@qq.com")
//                .gender("男")
//                .lastName("赵六")
//                .deptNo("02")
//                .build();
//        List<Employee> employeeList = Arrays.asList(zhangsan, lisi, wangwu,zhaoliu);
        boolean isSave = employeeService.saveBatch(employeeList);
        System.out.println(isSave);
    }

    /**
     * 测试批量插入(指定批次大小)
     * boolean saveBatch(Collection<T> entityList, int batchSize);
     */
    @Test
    public void testSaveBatchSetBatchSize() {
        Employee zhangsan = Employee.builder().email("1458687169@qq.com")
                .gender("男")
                .lastName("张三")
                .deptNo("01")
                .build();
        Employee lisi = Employee.builder().email("1358697169@qq.com")
                .gender("男")
                .lastName("李四")
                .deptNo("01")
                .build();
        Employee wangwu = Employee.builder().email("1958687169@qq.com")
                .gender("男")
                .lastName("王五")
                .deptNo("02")
                .build();
        Employee zhaoliu = Employee.builder().email("1868687169@qq.com")
                .gender("男")
                .lastName("赵六")
                .deptNo("02")
                .build();
        List<Employee> employeeList = Arrays.asList(zhangsan, lisi, wangwu,zhaoliu);
        //指定批次大小
        boolean isSave = employeeService.saveBatch(employeeList, 2);
        System.out.println(isSave);
    }

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
    public void testUpdateWithUpdateWrapper() {
        Employee zhangsan = Employee.builder().email("1458687169@qq.com")
                .gender("男")
                .lastName("张三三")
                .deptNo("01")
                .build();
        UpdateWrapper<Employee> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("gender","男");
        boolean isSaveOrUpdate = employeeService.saveOrUpdate(zhangsan,updateWrapper);
        System.out.println("isSaveOrUpdate = " + isSaveOrUpdate);
    }

    /**
     * 测试批量修改插入(不指定批次大小)
     * boolean saveOrUpdateBatch(Collection<T> entityList);
     */
    @Test
    public void testSaveOrUpdateBatchWithoutSetBatchSize() {
        Employee zhangsan = Employee.builder().email("1458687169@qq.com")
                .gender("男")
                .lastName("张三")
                .deptNo("01")
                .build();
        Employee lisi = Employee.builder().email("1358697169@qq.com")
                .gender("男")
                .lastName("李四")
                .deptNo("01")
                .build();
        Employee wangwu = Employee.builder().email("1958687169@qq.com")
                .gender("男")
                .lastName("王五")
                .deptNo("02")
                .build();
        Employee zhaoliu = Employee.builder().email("1868687169@qq.com")
                .gender("男")
                .lastName("赵六")
                .deptNo("02")
                .build();
        List<Employee> employeeList = Arrays.asList(zhangsan, lisi, wangwu,zhaoliu);
        boolean isSave = employeeService.saveOrUpdateBatch(employeeList);
        System.out.println(isSave);
    }

    /**
     * 测试批量修改插入(指定批次大小)
     * boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);
     */
    @Test
    public void testSaveOrUpdateBatchWithSetBatchSize() {
        Employee zhangsan = Employee.builder().email("1458687169@qq.com")
                .gender("男")
                .lastName("张三")
                .deptNo("01")
                .build();
        Employee lisi = Employee.builder().email("1358697169@qq.com")
                .gender("男")
                .lastName("李四")
                .deptNo("01")
                .build();
        Employee wangwu = Employee.builder().email("1958687169@qq.com")
                .gender("男")
                .lastName("王五")
                .deptNo("02")
                .build();
        Employee zhaoliu = Employee.builder().email("1868687169@qq.com")
                .gender("男")
                .lastName("赵六")
                .deptNo("02")
                .build();
        List<Employee> employeeList = Arrays.asList(zhangsan, lisi, wangwu,zhaoliu);
        //设置批次大小为2
        boolean isSave = employeeService.saveOrUpdateBatch(employeeList,2);
        System.out.println(isSave);
    }
}