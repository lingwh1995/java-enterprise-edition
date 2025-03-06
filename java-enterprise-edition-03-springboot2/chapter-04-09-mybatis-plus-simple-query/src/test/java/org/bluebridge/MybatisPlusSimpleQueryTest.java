package org.bluebridge;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SimpleQuery;
import org.bluebridge.entity.Employee;
import org.bluebridge.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * List<A> list(LambdaQueryWrapper<E> wrapper, SFunction<E, A> sFunction, Consumer<E>... peeks);    // 查询表内记录，封装返回为 List<属性>
 * List<A> list(LambdaQueryWrapper<E> wrapper, SFunction<E, A> sFunction, boolean isParallel, Consumer<E>... peeks);    // 查询表内记录，封装返回为 List<属性>，考虑了并行流的情况
 *
 *
 * Map<A, E> keyMap(LambdaQueryWrapper<E> wrapper, SFunction<E, A> sFunction, Consumer<E>... peeks);    // 查询表内记录，封装返回为 Map<属性,实体>
 * Map<A, E> keyMap(LambdaQueryWrapper<E> wrapper, SFunction<E, A> sFunction, boolean isParallel, Consumer<E>... peeks);    // 查询表内记录，封装返回为 Map<属性,实体>，考虑了并行流的情况
 *
 *
 * Map<A, P> map(LambdaQueryWrapper<E> wrapper, SFunction<E, A> keyFunc, SFunction<E, P> valueFunc, Consumer<E>... peeks);  // 查询表内记录，封装返回为 Map<属性,属性>
 * Map<A, P> map(LambdaQueryWrapper<E> wrapper, SFunction<E, A> keyFunc, SFunction<E, P> valueFunc, boolean isParallel, Consumer<E>... peeks);  // 查询表内记录，封装返回为 Map<属性,属性>，考虑了并行流的情况
 *
 *
 * Map<K, List<T>> group(LambdaQueryWrapper<T> wrapper, SFunction<T, K> sFunction, Consumer<T>... peeks);   // 查询表内记录，封装返回为 Map<属性,List<实体>>
 * Map<K, List<T>> group(LambdaQueryWrapper<T> wrapper, SFunction<T, K> sFunction, boolean isParallel, Consumer<T>... peeks);   // 查询表内记录，封装返回为 Map<属性,List<实体>>，考虑了并行流的情况
 * M group(LambdaQueryWrapper<T> wrapper, SFunction<T, K> sFunction, Collector<? super T, A, D> downstream, Consumer<T>... peeks);  // 查询表内记录，封装返回为 Map<属性,分组后对集合进行的下游收集器>
 * M group(LambdaQueryWrapper<T> wrapper, SFunction<T, K> sFunction, Collector<? super T, A, D> downstream, boolean isParallel, Consumer<T>... peeks);  // 查询表内记录，封装返回为 Map<属性,分组后对集合进行的下游收集器>，考虑了并行流的情况
 */
@SpringBootTest
public class MybatisPlusSimpleQueryTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    public void init() {
        //删除数据库中数据
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
     * // 查询表内记录，封装返回为 List<属性>
     * List<A> list(LambdaQueryWrapper<E> wrapper, SFunction<E, A> sFunction, Consumer<E>... peeks);
     *
     * // 查询表内记录，封装返回为 List<属性>，考虑了并行流的情况
     * List<A> list(LambdaQueryWrapper<E> wrapper, SFunction<E, A> sFunction, boolean isParallel, Consumer<E>... peeks);
     */
    @Test
    public void testSimpleQueryList() {
        List<String> lastNameList = new ArrayList<>();
        // 假设有一个 Employee 实体类和对应的 BaseMapper
        List<Long> ids = SimpleQuery.list(
                Wrappers.lambdaQuery(Employee.class), // 使用 lambda 查询构建器
                Employee::getId, // 提取的字段，这里是 User 的 id
                System.out::println, // 第一个 peek 操作，打印每个用户
                employee -> lastNameList.add(employee.getLastName()) // 第二个 peek 操作，将每个用户的名字添加到 userNames 列表中
        );
        System.out.println("lastNameList = " + lastNameList);
        System.out.println("ids = " + ids);
    }

    /**
     * // 查询表内记录，封装返回为 Map<属性,实体>
     * Map<A, E> keyMap(LambdaQueryWrapper<E> wrapper, SFunction<E, A> sFunction, Consumer<E>... peeks);
     * // 查询表内记录，封装返回为 Map<属性,实体>，考虑了并行流的情况
     * Map<A, E> keyMap(LambdaQueryWrapper<E> wrapper, SFunction<E, A> sFunction, boolean isParallel, Consumer<E>... peeks);
     */
    @Test
    public void testSimpleQueryKeyMap() {
        // 假设有一个 Employee 实体类和对应的 BaseMapper
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //设置查询条件
        queryWrapper.eq(Employee::getGender, "男");

        // 使用 keyMap 方法查询并封装结果
        Map<String, Employee> employeeMap = SimpleQuery.keyMap(
                queryWrapper, // 查询条件构造器
                Employee::getLastName, // 使用用户名作为键
                employee -> System.out.println("Processing employee: " + employee.getLastName()) // 打印处理的用户名
        );

        // 遍历结果
        for (Map.Entry<String, Employee> entry : employeeMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }

    /**
     * // 查询表内记录，封装返回为 Map<属性,属性>
     * Map<A, P> map(LambdaQueryWrapper<E> wrapper, SFunction<E, A> keyFunc, SFunction<E, P> valueFunc, Consumer<E>... peeks);
     *
     * // 查询表内记录，封装返回为 Map<属性,属性>，考虑了并行流的情况
     * Map<A, P> map(LambdaQueryWrapper<E> wrapper, SFunction<E, A> keyFunc, SFunction<E, P> valueFunc, boolean isParallel, Consumer<E>... peeks);
     */
    @Test
    public void testSimpleQueryMap() {
        // 假设有一个 Employee 实体类和对应的 BaseMapper
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //设置查询条件
        queryWrapper.eq(Employee::getGender, "男");

        // 使用 map 方法查询并封装结果
        Map<String, String> employeeMap = SimpleQuery.map(
                queryWrapper, // 查询条件构造器
                Employee::getLastName, // 使用用户名作为键
                Employee::getDeptNo, // 使用部门编号作为值
                employee -> System.out.println("Processing employee: " + employee.getLastName())
        );

        // 遍历结果
        for (Map.Entry<String, String> entry : employeeMap.entrySet()) {
            System.out.println("lastName: " + entry.getKey() + ", deptNo: " + entry.getValue());
        }
    }


    /**
     * // 查询表内记录，封装返回为 Map<属性,List<实体>>
     * Map<K, List<T>> group(LambdaQueryWrapper<T> wrapper, SFunction<T, K> sFunction, Consumer<T>... peeks);
     *
     * // 查询表内记录，封装返回为 Map<属性,List<实体>>，考虑了并行流的情况
     * Map<K, List<T>> group(LambdaQueryWrapper<T> wrapper, SFunction<T, K> sFunction, boolean isParallel, Consumer<T>... peeks);
     *
     * // 查询表内记录，封装返回为 Map<属性,分组后对集合进行的下游收集器>
     * M group(LambdaQueryWrapper<T> wrapper, SFunction<T, K> sFunction, Collector<? super T, A, D> downstream, Consumer<T>... peeks);
     *
     * // 查询表内记录，封装返回为 Map<属性,分组后对集合进行的下游收集器>，考虑了并行流的情况
     * M group(LambdaQueryWrapper<T> wrapper, SFunction<T, K> sFunction, Collector<? super T, A, D> downstream, boolean isParallel, Consumer<T>... peeks);
     */
    @Test
    public void testSimpleQueryGroup() {
        // 假设有一个 Employee 实体类和对应的 BaseMapper
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //设置查询条件
        queryWrapper.eq(Employee::getGender, "男");

        // 使用 group 方法查询并封装结果，按照用户名分组
        Map<String, List<Employee>> employeeGroup = SimpleQuery.group(
                queryWrapper, // 查询条件构造器
                Employee::getLastName, // 使用用户名作为分组键
                employee -> System.out.println("Processing employee: " + employee.getLastName()) // 打印处理的用户名
        );

        // 遍历结果
        for (Map.Entry<String, List<Employee>> entry : employeeGroup.entrySet()) {
            System.out.println("lastName: " + entry.getKey());
            for (Employee employee : entry.getValue()) {
                System.out.println(" - Employee: " + employee);
            }
        }
    }
}
