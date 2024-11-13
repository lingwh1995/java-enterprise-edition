package org.bluebridge.propertySource;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author ronin
 */
public class User {
    /**
     * 使用@Value赋值:
     *  1.基本数值
     *  2.可以写SpEL
     *  3.可以写${},取出配置文件中的值
     */
    //使用@Value直接赋值
    @Value("zhangsan")
    private String name;
    //使用SpEL直接执行计算
    @Value("#{20-4}")
    private Integer age;
    //读取properties中的配置
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
