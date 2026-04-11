package org.bluebridge.profile.base.bysetter;

/**
 * set方式注入
 */
public class UserInjectValueBySetter {
    private String type;
    private String name;
    private Integer age;

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserInjectBySetter{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
