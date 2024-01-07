package bluebridge.dao;

import bluebridge.domain.Employee;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class EmployeeDao {

    private static Map<Integer, Employee> mockDatabase = null;
    static {
        mockDatabase = new HashMap<>();
        mockDatabase.put(1001,new Employee(1001,"张三","zhangsan@gmail.com",1));
        mockDatabase.put(1002,new Employee(1002,"李四","lisi@gmail.com",1));
        mockDatabase.put(1003,new Employee(1003,"王五","wangwu@gmail.com",1));
    }

    private static Integer initId = 1004;
    /**
     * 增加员工信息
     * @param employee
     */
    public void addEmployee(Employee employee) {
        employee.setId(initId++);
        mockDatabase.put(employee.getId(),employee);
    }

    /**
     * 删除员工信息
     * @param id
     */
    public void deleteEmployeeById(Integer id) {
        mockDatabase.remove(id);
        System.out.println(mockDatabase);
    }

    /**
     * 修改员工信息
     * @param employee
     */
    public void updateEmployee(Employee employee) {
        mockDatabase.replace(employee.getId(),employee);
    }

    /**
     * 根据id查询单个员工信息
     * @param id
     */
    public Employee getEmployeeById(Integer id) {
        return mockDatabase.get(id);
    }

    /**
     * 查询全部员工信息
     */
    public Collection<Employee> getAllEmployees() {
        return mockDatabase.values();
    }
}
