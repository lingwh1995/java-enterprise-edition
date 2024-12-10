package org.bluebridge.annotation.demo.datasource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

@Component
public class MyDataSource1 implements DataSource {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MyDataSource1.class);

    /**
     * 数据库连接需要的四个参数
     */
    @Value("com.mysql.jdbc.Driver")
    private String driver;
    @Value("demo")
    private String username;
    @Value("123456")
    private String password;
    @Value("jdbc:mysql://localhost:3306/mydataSource1")
    private String url;


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
