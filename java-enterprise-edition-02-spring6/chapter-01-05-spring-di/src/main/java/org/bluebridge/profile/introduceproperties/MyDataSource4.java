package org.bluebridge.profile.introduceproperties;

import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 使用 context:property-placeholder 引入外部的properties文件
 */
public class MyDataSource4 implements DataSource {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MyDataSource4.class);

    /**
     * 数据库连接需要的四个参数
     */
    private String driver;
    private String username;
    private String password;
    private String url;

    /**
     * 使用set方式注入为属性赋值
     * @param driver
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public Connection getConnection() throws SQLException {
        logger.info("driver:"+driver);
        logger.info("username:"+username);
        logger.info("password:"+password);
        logger.info("url:"+url);
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
