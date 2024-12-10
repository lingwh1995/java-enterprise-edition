package org.bluebridge.profile.namespace.pnamespace;

import org.bluebridge.profile.namespace.domain.Cat;

/**
 * set方式注入之P命名空间注入  其底层还是依赖set方式实现注入，只不过P命名空间注入能让Spring的配置变得更为简单
 */
public class UserInjectValueByPNamespace {
    private String name;
    private String age;
    private Cat cat;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    @Override
    public String toString() {
        return "UserInjectValueByPNamespace{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", cat=" + cat +
                '}';
    }
}
