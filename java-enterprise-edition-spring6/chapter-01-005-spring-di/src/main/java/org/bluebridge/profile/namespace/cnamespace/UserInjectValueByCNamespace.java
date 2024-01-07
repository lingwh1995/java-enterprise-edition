package org.bluebridge.profile.namespace.cnamespace;

import org.bluebridge.profile.namespace.domain.Cat;

/**
 * 构造方式注入之C命名空间注入  其底层还是依赖构造方式实现注入，只不过C命名空间注入能让Spring的配置变得更为简单
 */
public class UserInjectValueByCNamespace {
    private String name;
    private String age;
    private Cat cat;

    public UserInjectValueByCNamespace(String name, String age, Cat cat) {
        this.name = name;
        this.age = age;
        this.cat = cat;
    }

    @Override
    public String toString() {
        return "UserInjectValueByCNamespace{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", cat=" + cat +
                '}';
    }
}
