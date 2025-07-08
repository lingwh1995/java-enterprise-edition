package org.bluebridge.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author lingwh
 * @desc   测试使用自己创建的数据源创建JdbcTemplate
 * @date   2019/3/25 14:32
 */
public class JdbcTemplateDataSourceTest {

    private JdbcTemplate jdbcTemplate;

    @Before
    public void init(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://host.docker.internal:3306/javaee");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        // 创建JdbcTemplate
        jdbcTemplate = new JdbcTemplate(dataSource);
        System.out.println(jdbcTemplate);
        System.out.println("-----------------------------");
    }

    @Test
    public void run1(){
        // 完成数据的添加
        try {
            int update = jdbcTemplate.update("insert into t_account values (?,?,?)", new Object[]{"001", "测试", 10000});
            System.out.println("受影响的行数:"+update);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
