package org.bluebridge;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.bluebridge.entity.Employee;
import org.bluebridge.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * int insert(T entity);    // 插入一条记录
 */
@SpringBootTest
public class MybatisPlusInsertTest {
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 首先执行这个方法初始化数据库数据
     */
    @Test
    public void init(){
        //删除数据库中t_employee表中所有数据
        int delete = employeeMapper.delete(new QueryWrapper<>());
        System.out.println("delete = " + delete);
    }

    /**
     * 测试插入一条记录（选择字段，策略插入）
     */
    @Test
    public void testInsert() {
        Employee employee = Employee.builder()
                .id(1l)
                .email("1111111111@qq.com")
                .gender("男")
                .lastName("张一")
                .deptNo("01")
                .build();
        int insert = employeeMapper.insert(employee);
        System.out.println(insert);
    }
}
