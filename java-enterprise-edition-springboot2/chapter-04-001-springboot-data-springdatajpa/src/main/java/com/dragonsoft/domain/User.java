package com.dragonsoft.domain;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 可以省略的注解:
 *      @Table(name="T_USER")
 *      @Basic
 *      @Column(name="age")
 *  不可以省略的注解:
 *      @Transient  --> 这个字段不会被持久化到数据库中
 *      在xml方式中配置该字段也可以实现这个效果
 *  只是省略了User.hbm.xml,但是仍然要把这个实体注册到全局配置文件中
 */
//类-->
@Entity
@Table(name="T_USER")
public class User {
    //属性-->字段
    @Id
    //@GeneratedValue(strategy= GenerationType.AUTO)
    @GeneratedValue(generator="hibernate-built-in")
    @GenericGenerator(name="hibernate-built-in", strategy = "uuid")
    @Column(name="id")
    private String id;

    @Basic//--> 说明这个字段会被持久化到数据库中，可以省略
    @Column(name="name")
    private String name;

//    @Basic
//    @Column(name="age")
    private Integer age;

    @Basic
    @Column(name="gender")
    private String gender;

    public User() {

    }

    public User(String id, String name, Integer age, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
