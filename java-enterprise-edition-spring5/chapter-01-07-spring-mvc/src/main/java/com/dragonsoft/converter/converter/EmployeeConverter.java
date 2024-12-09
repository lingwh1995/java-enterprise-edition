package com.dragonsoft.converter.converter;

import com.dragonsoft.converter.entity.Employee;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.Arrays;


/**
 * @author ronin
 * @version V1.0
 * @description 把字符串类型的Employee转换为实体类型的Employee
 * @class EmployeeConverter
 * @date 2019/6/17 14:58
 */
public class EmployeeConverter implements Converter<String,Employee>{

    @Nullable
    @Override
    public Employee convert(String source) {
        //employee=1-张三-123456-18
        //employee=id-username-password-age
        String[] employeeStr = source.trim().split("-");
        System.out.println("employeeStr"+Arrays.toString(employeeStr));
        Employee employee = new Employee(employeeStr[0],employeeStr[1],employeeStr[2],employeeStr[3]);
        return employee;
    }
}
