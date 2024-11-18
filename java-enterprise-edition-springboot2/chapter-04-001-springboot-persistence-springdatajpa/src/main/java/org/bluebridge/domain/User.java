package org.bluebridge.domain;


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
@Table(name="t_user")
public class User {
    //属性-->字段
    @Id
    //@GeneratedValue(strategy= GenerationType.AUTO)
    @GeneratedValue(generator="hibernate-built-in")
    @GenericGenerator(name="hibernate-built-in", strategy = "uuid")
    @Column(name="id")
    private String id;

    @Basic//--> 说明这个字段会被持久化到数据库中，可以省略
    @Column(name="username")
    private String username;

//    @Basic
//    @Column(name="password")
    private String password;

    public User() {
    }

    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
