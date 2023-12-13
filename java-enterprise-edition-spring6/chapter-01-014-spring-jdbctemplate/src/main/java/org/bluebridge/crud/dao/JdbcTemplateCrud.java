package org.bluebridge.crud.dao;

import org.bluebridge.crud.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 使用JdbcTemplate完成增、删、改、查(查一个、查多个)
 * 使用RowMapper接口的实现类实现把返回结果封装到任意数据结构中
 */
@Component
public class JdbcTemplateCrud {

    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplateCrud.class);

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public void init(List<User> users) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            String sql = "insert into t_user(id,name,age) values(?,?,?)";
            jdbcTemplate.update(sql, user.getId(),user.getName(),user.getAge());
        }
    }

    public void truncate() {
        String sql = "truncate t_user";
        jdbcTemplate.execute(sql);
    }

    public int addUser(User user) {
        String sql = "insert into t_user(id,name,age) values(?,?,?)";
        return jdbcTemplate.update(sql,user.getId(),user.getName(),user.getAge());
    }

    public int deleteUserById(String id) {
        String sql = "delete from t_user where id = ?";
        return jdbcTemplate.update(sql,id);
    }

    public int updateUser(User user) {
        String sql = "update t_user set name = ?, age = ? where id = ?";
        return jdbcTemplate.update(sql,user.getName(),user.getAge(),user.getId());
    }

    /**
     * 查询一个对象
     * @param id
     * @return
     */
    public User queryOneObject(String id) {
        String sql = "select * from t_user where id = ?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),id);
    }

    /**
     * 查询一个对象或者多个对象
     * @param age
     * @return
     */
    public List<User> queryMoreThanOneObject(Integer age) {
        String sql = "select * from t_user where age >= ?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(User.class),age);
    }

    /**
     * 查询所有对象，把查询到的结果封装到List中
     * @return
     */
    public List<User> queryAll() {
        String sql = "select * from t_user";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(User.class));
    }

    /**
     * 查询一个值：查询数据库中总记录数
     * @return
     */
    public Integer queryOneValue_1() {
        String sql = "select count(*) from t_user";
        return jdbcTemplate.queryForObject(sql, int.class);
    }

    /**
     * 查询一个值：查询id为001的记录中name字段的值
     * @return
     */
    public String queryOneValue_2(String id) {
        String sql = "select name from t_user where id = ?";
        return jdbcTemplate.queryForObject(sql, String.class,id);
    }

    /**
     * 查询所有的姓名，并把查询结果封装到一个List<String>中
     * @return
     */
    public List<String> querySingleColumn() {
        String sql = "select name from t_user";
        return jdbcTemplate.query(sql, new SingleColumnRowMapper<>(String.class));
    }

    /**
     * 查询一个对象，把查询到的结果封装到Map中
     * @return
     */
    public Map<String,String> queryOneObjectUseRowMapper(String id) {
        String sql = "select * from t_user where id = ?";
        return jdbcTemplate.queryForObject(sql,new BeanMapperMap(),id);
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
/**
 * 把单条查询结果封装到Map中
 */
class BeanMapperMap implements RowMapper<Map<String,String>> {

    @Override
    public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
        HashMap userInfo = new HashMap<String, String>();
        userInfo.put("id",rs.getString("id"));
        userInfo.put("name",rs.getString("name"));
        userInfo.put("id",rs.getInt("age"));
        return userInfo;
    }
}
