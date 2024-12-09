package org.bluebridge.profile.base;

/**
 * 构造方式注入
 */
public class UserInjectValueByConstructor {
    private String type;
    private String name;
    private Integer age;

    public UserInjectValueByConstructor(String type, String name, Integer age) {
        this.type = type;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserInjectByConstructor{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
