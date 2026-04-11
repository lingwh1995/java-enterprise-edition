package org.bluebridge.profile.dibysetsenior.inject;


/**
 * set方式注入专题之注入   NULL和空字符串
 */
public class UserInjectNULLAndEmptyStringBySetter {

    private Integer id;
    private String name;
    private String age;

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 用于测试注入的值是NULL还是空字符串
     * @return
     */
    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    /**
     * 用于测试注入的值是NULL还是空字符串
     * @return
     */
    public String getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "UserInjectNULLAndEmptyStringBySetter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
