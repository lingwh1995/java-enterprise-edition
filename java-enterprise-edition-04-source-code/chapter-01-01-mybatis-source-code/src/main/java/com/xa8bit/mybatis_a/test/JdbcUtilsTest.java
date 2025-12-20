package com.xa8bit.mybatis_a.test;

import com.xa8bit.mybatis_a.entity.Configuration;
import com.xa8bit.mybatis_a.util.JdbcUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author lingwh
 * @desc 测试 JdbcUtils
 * @date 2025/12/20 13:11
 */
public class JdbcUtilsTest {

    /**
     * 测试基于JDBC打印完整SQL语句
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    public void testPrintSQL() throws SQLException, ClassNotFoundException {
        // 构建连接属性配置对象
        Configuration configuration = new Configuration();
        configuration.setDriver("com.mysql.cj.jdbc.Driver");
        configuration.setUrl("jdbc:mysql://localhost:3306/javaee");
        configuration.setUsername("root");
        configuration.setPassword("123456");

        // 测试获取数据库连接
        Connection connection = JdbcUtils.getConnection(configuration);
        String sql = "select * from t_employee where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, 1);

        String completeSql = preparedStatement.toString();
        System.out.println("完整SQL语句 = " + completeSql);

        connection.close();
    }

}
