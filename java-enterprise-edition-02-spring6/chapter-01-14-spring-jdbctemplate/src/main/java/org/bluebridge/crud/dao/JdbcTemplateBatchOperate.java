package org.bluebridge.crud.dao;

import org.bluebridge.crud.domain.User;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 使用JdbcTemplate提供的批处理操作数据库
 */
@Component
public class JdbcTemplateBatchOperate {

    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplateBatchOperate.class);

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
     * 批量新增数据方式一    非常推荐
     *      使用List作为参数，该List泛型类型为Object[]
     * @return
     */
    public int[] batchInsert1(List<Object[]> users) {
        String sql = "insert into t_user(id,name,age) values(?,?,?)";
        return jdbcTemplate.batchUpdate(sql, users);
    }

    /**
     * 批量新增数据方式二    不推荐使用
     *           有可能引起sql注入问题
     * @return
     */
    public int[] batchInsert2() {
        String sql1 = "insert into t_user(id,name,age) values('004','赵六',22)";
        String sql2 = "insert into t_user(id,name,age) values('005','孙七',28)";
        return jdbcTemplate.batchUpdate(sql1, sql2);
    }

    /**
     * 批量新增数据方式三    一般推荐
     *      使用匿名内部类实现批量新增
     * @return
     */
    public int[] batchInsert3(List<User> users) {
        String sql = "insert into t_user(id,name,age) values(?,?,?)";
        BatchPreparedStatementSetter bps = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1,users.get(i).getId());
                ps.setString(2,users.get(i).getName());
                ps.setInt(3,users.get(i).getAge());
            }

            @Override
            public int getBatchSize() {
                return users.size();
            }
        };
        return jdbcTemplate.batchUpdate(sql, bps);
    }

    /**
     * 批量更新方式一  非常推荐
     * @param users
     * @return
     */
    public int[] batchUpdate1(List<Object[]> users) {
        String sql = "update t_user set name = ? ,age = ? where id = ?";
        return jdbcTemplate.batchUpdate(sql,users);
    }

    /**
     * 批量更新数据方式二    不推荐使用
     *           有可能引起sql注入问题
     * @return
     */
    public int[] batchUpdate2() {
        String sql1 = "update t_user set name = '张三修改后', age = '28' where id = '001'";
        String sql2 = "update t_user set name = '李四修改后', age = '28' where id = '002'";
        String sql3 = "update t_user set name = '王五修改后', age = '28' where id = '003'";
        return jdbcTemplate.batchUpdate(sql1, sql2,sql3);
    }

    /**
     * 批量新增数据方式三(一般推荐使用)
     *      使用匿名内部类实现批量新增
     * @return
     */
    public int[] batchUpdate3(List<User> users) {
        String sql = "update t_user set name = ?,age = ? where id = ?";
        BatchPreparedStatementSetter bps = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1,users.get(i).getName());
                ps.setInt(2,users.get(i).getAge());
                ps.setString(3,users.get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return users.size();
            }
        };
        return jdbcTemplate.batchUpdate(sql, bps);
    }

    /**
     * 批量删除方式一  非常推荐
     * @param users
     * @return
     */
    public int[] batchDelete1(List<Object[]> users) {
        String sql = "delete from t_user where id = ?";
        return jdbcTemplate.batchUpdate(sql,users);
    }


    /**
     * 批量删除据方式二 不推荐使用
     *      有可能引起sql注入问题
     * @return
     */
    public int[] batchDelete2() {
        String sql1 = "delete from t_user where id = '001'";
        String sql2 = "delete from t_user where id = '002'";
        return jdbcTemplate.batchUpdate(sql1, sql2);
    }


    /**
     * 展示所有的用户
     */
    public void showAllUser() {
        String sql = "select * from t_user";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()) {
            User user = iterator.next();
            logger.info("id: " + user.getId() + ",name: " + user.getName() + ",age: " + user.getAge());
        }
    }
}
