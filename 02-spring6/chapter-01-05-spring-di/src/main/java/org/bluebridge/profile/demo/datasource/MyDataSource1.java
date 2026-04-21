package org.bluebridge.profile.demo.datasource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * Set方式注入经典应用，getConnection()获取连接对象时需要用到的四个参数可以通过set方式注入来实现外部注入，从而分离参数与代码
 */
public class MyDataSource1 implements DataSource {

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
        System.out.println("driver = " + driver);
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        System.out.println("url = " + url);
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
