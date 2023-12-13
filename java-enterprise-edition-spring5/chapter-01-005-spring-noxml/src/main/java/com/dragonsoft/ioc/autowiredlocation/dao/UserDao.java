package com.dragonsoft.ioc.autowiredlocation.dao;

import com.dragonsoft.ioc.autowiredlocation.dbutils.Dbutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author ronin
 */
@Repository
public class UserDao {

    private Dbutils dbutils;

    /**
     * 测试使用@Autowired注解标注在构造方法中
     * 注意:如果只有一个有参构造器,参数位置的@Autowired可以省略,参数位置的组件可以自动从容器中获取
     * @param dbutils
     */
    public UserDao(@Autowired Dbutils dbutils) {
        this.dbutils = dbutils;
    }

    //注意:这个写法等同于上面的写法
//    public UserDao(Dbutils dbutils) {
//        this.dbutils = dbutils;
//    }

    public Dbutils getDbutils() {
        return dbutils;
    }

    /**
     * 把@Auwired 标注在setter方法的参数位置是无效的,只能标注在构造方法的参数位置
     * @param dbutils
     */
    public void setDbutils(Dbutils dbutils) {
        this.dbutils = dbutils;
    }

    public void say() {
        dbutils.say();
    }

    @Override
    public String toString() {
        return "UserDao{" +
                "dbutils=" + dbutils +
                '}';
    }
}
