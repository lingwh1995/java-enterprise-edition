package mybatis_a.entity;
import	java.util.Properties;
import mybatis_a.mapper.MapperStatement;

import	java.util.HashMap;

import java.util.Map;

/**
 * @author ronin
 */
public class Configuration {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private Map<String,MapperStatement> mapStatements = new HashMap<> ();
    /**保存所有的配置文件*/
    private final Properties properties = new Properties();
    /**保存所有的数据库配置信息*/
    private final Map<String,String> dbConfigMap = new HashMap<>();

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
