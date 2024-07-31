package com.dragonsoft.configuration.dao;

import com.dragonsoft.configuration.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDao {

    private JdbcTemplate jdbcTemplate;
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addUser(User user) {
        System.out.println("jdbcTemplate = " + jdbcTemplate);
        System.out.println("模拟保存用户成功......");
        return 1;
    }
}
