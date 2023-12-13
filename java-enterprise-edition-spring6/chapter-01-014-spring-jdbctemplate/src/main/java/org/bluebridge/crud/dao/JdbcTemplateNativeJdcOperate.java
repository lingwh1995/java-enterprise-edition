package org.bluebridge.crud.dao;

import org.bluebridge.crud.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * JdbcTemplate提供的原生Jdbc操作方法，需要使用回调函数实现
 */
@Component
public class JdbcTemplateNativeJdcOperate {

    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplateNativeJdcOperate.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 初始化数据库
     */
    public void init() {
        String sql = "insert into t_user(id,name,age) values(?,?,?)";
        List<Object[]> users = new ArrayList<>();
        users.add(new Object[]{"001","张三",18});
        users.add(new Object[]{"002", "李四", 19});
        users.add(new Object[]{"003","王五",20});
        jdbcTemplate.batchUpdate(sql, users);
    }

    /**
     * 清空数据库
     */
    public void truncate() {
        String sql = "truncate t_user";
        jdbcTemplate.execute(sql);
    }

    /**
     * 查询所有数据
     * @return
     */
    public User queryOneObject(String id) {
        String sql = "select * from t_user where id = ?";
        User user = jdbcTemplate.execute(sql, new PreparedStatementCallback<User>() {
            @Override
            public User doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1,id);
                ResultSet resultSet = ps.executeQuery();
                User user = null;
                while (resultSet.next()) {
                    user = new User(id,resultSet.getString("name"),resultSet.getInt("age"));
                }
                return user;
            }
        });
        return user;
    }
    /**
     * 查询所有数据
     * @return
     */
    public List<User> queryAll() {
        String sql = "select * from t_user";
        List<User> userList = jdbcTemplate.execute(sql, new PreparedStatementCallback<List<User>>() {
            @Override
            public List<User> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                List<User> users = new ArrayList<>();
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    users.add(new User(id, name, age));
                }
                return users;
            }
        });
        return userList;
    }


    /**
     * 展示所有用户信息
     */
    public void showAllUser() {
        String sql = "select * from t_user";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
        Iterator<User> iterator = users.iterator();
        logger.info("当前数据库中用户信息如下:");
        while (iterator.hasNext()) {
            User user = iterator.next();
            logger.info("id: " + user.getId() + ",name: " + user.getName() + ",age: " + user.getAge());
        }
    }
}
