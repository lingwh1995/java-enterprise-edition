package com.xa8bit.mybatis_a.entity;

import com.xa8bit.mybatis_a.mapper.MapperStatement;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author ronin
 */
public class Configuration {

    /** 数据库连接驱动类名 */
    private String driver;

    /** 数据库连接URL */
    private String url;

    /** 数据库连接用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 保存所有的MapperStatement对象 */
    private Map<String,MapperStatement> mapStatements = new HashMap<> ();

    /** 保存所有的配置文件 */
    private final Properties properties = new Properties();

    /** 保存所有的数据库配置信息 */
    private final Map<String,String> dbConfigMap = new HashMap<>();

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
        dbConfigMap.put("driver",driver);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        dbConfigMap.put("url",url);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        dbConfigMap.put("username",username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        dbConfigMap.put("password",password);
    }

    public Map<String, MapperStatement> getMapStatements() {
        return mapStatements;
    }

    public void setMapStatements(Map<String, MapperStatement> mapStatements) {
        this.mapStatements = mapStatements;
    }

    public Properties getProperties() {
        return properties;
    }

    public Map<String,String> getDbConfigMap() {
        return dbConfigMap;
    }

}
