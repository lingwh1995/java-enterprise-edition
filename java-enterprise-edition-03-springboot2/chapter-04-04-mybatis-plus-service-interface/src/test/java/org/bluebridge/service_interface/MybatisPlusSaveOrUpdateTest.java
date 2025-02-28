package org.bluebridge.service_interface;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.bluebridge.entity.Employee;
import org.bluebridge.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 *  boolean saveOrUpdate(T entity);  // TableId 注解属性值存在则更新记录，否插入一条记录
 *  boolean saveOrUpdate(T entity, Wrapper<T> updateWrapper);  // 根据updateWrapper尝试更新，否继续执行saveOrUpdate(T)方法
 *  boolean saveOrUpdateBatch(Collection<T> entityList); // 批量修改插入
 *  boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);  // 批量修改插入
 */
@SpringBootTest
public class MybatisPlusSaveOrUpdateTest {

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
    }

    /**
     * 测试TableId 注解属性值存在则更新记录，否插入一条记录
     * boolean saveOrUpdate(T entity);
     */
    @Test
    public void testSaveOrUpdate() {
        Employee zhangsan = Employee.builder().email("1111111111@qq.com")
                .id(1l)
                .gender("男")
                .lastName("张一")
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
                .gender("女")
                .lastName("张一")
                .deptNo("01")
                .build();
        UpdateWrapper<Employee> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",1l);
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
                new Employee(1l,"张三", "1111111111@qq.com", "男", "01"),
                new Employee(2l,"李四", "2222222222@qq.com", "女", "02"),
                new Employee(3l,"王五", "3333333333@qq.com", "男", "03"),
                new Employee(4l,"赵六", "44444444444@qq.com", "男", "03")
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
                new Employee(1l,"张三三", "1111111111@qq.com", "男", "01"),
                new Employee(2l,"李四四", "2222222222@qq.com", "女", "02"),
                new Employee(null,"王五五", "3333333333@qq.com", "男", "03"),
                new Employee(null,"赵六六", "44444444444@qq.com", "男", "03")
        );
        //设置批次大小为2
        boolean isSave = employeeService.saveOrUpdateBatch(employeeList,2);
        System.out.println(isSave);
    }
}
