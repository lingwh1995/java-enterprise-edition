package com.dragonsoft.ioc.autowiredlocation.config;

import com.dragonsoft.ioc.autowiredlocation.dao.UserDao;
import com.dragonsoft.ioc.autowiredlocation.dbutils.Dbutils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ronin
 */
@ComponentScan({"com.dragonsoft.ioc.autowiredlocation.controller",
        "com.dragonsoft.ioc.autowiredlocation.service",
        "com.dragonsoft.ioc.autowiredlocation.dao",
        "com.dragonsoft.ioc.autowiredlocation.dbutils"})
@Configuration
public class Config {

    /**
     * @param dbutils 这个参数是容器中获取的
     * @return
     */

    @Bean(name="userDao2")
    public UserDao userDao(Dbutils dbutils){
        UserDao userDao = new UserDao(dbutils);
        return userDao;
    }
}
