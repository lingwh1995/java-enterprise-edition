package org.bluebridge.beaninstantiation.factorybeaninterface.demo;

import java.util.Date;

public class User {

    private String id;
    private String name;
    private String age;
    private Date birthday;
    private Date updateTime;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", birthday=" + birthday +
                ", updateTime=" + updateTime +
                '}';
    }
}
