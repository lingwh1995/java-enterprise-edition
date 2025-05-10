package org.bluebridge.ioc.value.domain;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author ronin
 */
public class Person {
    /**
     * 使用@Value赋值:
     *  1.基本数值
     *  2.可以写SpEL
     *  3.可以写${},取出配置文件中的值
     */
    @Value("zhangsan")
    private String name;
    @Value("#{20-4}")
    private Integer age;
    @Value("${person.school}")
    private String school;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", school='" + school + '\'' +
                '}';
    }
}
