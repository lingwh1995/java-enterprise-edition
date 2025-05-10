package org.bluebridge.ioc.autowiredlocation.config;

import org.bluebridge.ioc.autowiredlocation.dao.UserDao;
import org.bluebridge.ioc.autowiredlocation.dbutils.Dbutils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ronin
 */
@ComponentScan({"org.bluebridge.ioc.autowiredlocation.controller",
        "org.bluebridge.ioc.autowiredlocation.service",
        "org.bluebridge.ioc.autowiredlocation.dao",
        "org.bluebridge.ioc.autowiredlocation.dbutils"})
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
