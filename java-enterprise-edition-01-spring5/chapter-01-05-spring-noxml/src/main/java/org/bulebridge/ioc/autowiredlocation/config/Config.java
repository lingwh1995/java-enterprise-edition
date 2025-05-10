package org.bulebridge.ioc.autowiredlocation.config;

import org.bulebridge.ioc.autowiredlocation.dao.UserDao;
import org.bulebridge.ioc.autowiredlocation.dbutils.Dbutils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ronin
 */
@ComponentScan({"org.bulebridge.ioc.autowiredlocation.controller",
        "org.bulebridge.ioc.autowiredlocation.service",
        "org.bulebridge.ioc.autowiredlocation.dao",
        "org.bulebridge.ioc.autowiredlocation.dbutils"})
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
