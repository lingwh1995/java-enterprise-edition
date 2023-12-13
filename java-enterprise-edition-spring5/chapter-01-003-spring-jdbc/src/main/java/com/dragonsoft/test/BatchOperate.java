package com.dragonsoft.test;

import com.dragonsoft.domain.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ronin
 * @version V1.0
 * @desc    SpringJdbcTemplate的批量操作
 * @since 2019/7/24 16:03
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class BatchOperate {

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 初始化数据
     */
    @Test
    public void init(){
        jdbcTemplate.update("INSERT INTO T_ACCOUNT VALUES (?,?,?)", "001","张三",10);
        jdbcTemplate.update("INSERT INTO T_ACCOUNT VALUES (?,?,?)", "002","李四",20);
        jdbcTemplate.update("INSERT INTO T_ACCOUNT VALUES (?,?,?)", "003","张三",30);
        jdbcTemplate.update("INSERT INTO T_ACCOUNT VALUES (?,?,?)", "004","王五",40);
        jdbcTemplate.update("INSERT INTO T_ACCOUNT VALUES (?,?,?)", "005","赵六",50);
    }

    /**
     * 批量增加操作
     */
    @Test
    public void fun1(){
        String batchInsertSql1 = "INSERT INTO T_ACCOUNT VALUES ('001','测试批量操作',10)";
        String batchInsertSql2 = "INSERT INTO T_ACCOUNT VALUES ('002','测试批量操作',10)";
        jdbcTemplate.batchUpdate(batchInsertSql1,batchInsertSql2);
    }

    /**
     * 批量增加操作:使用匿名内部类完成数据封装
     */
    @Test
    public void fun2(){
        String batchInsertSql = "INSERT INTO T_ACCOUNT(ID,NAME,MONEY) VALUES (?,?,?)";
        List<Account> paramList = new ArrayList<Account>(){{
            add(new Account("003", "zhangsan", 28.9));
            add(new Account("004", "lisi", 68.0));
            add(new Account("005", "wangwu", 58.2));
        }};

        BatchPreparedStatementSetter bpss = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,paramList.get(i).getId());
                preparedStatement.setString(2,paramList.get(i).getName());
                preparedStatement.setDouble(3,paramList.get(i).getMoney());
            }

            @Override
            public int getBatchSize() {
                return paramList.size();
            }
        };
        jdbcTemplate.batchUpdate(batchInsertSql,bpss);
    }

    /**
     * 批量增加操作:不使用匿名内部类内部类完成数据封装
     */
    @Test
    public void fun3(){
        String batchInsertSql = "INSERT INTO T_ACCOUNT(ID,NAME,MONEY) VALUES (?,?,?)";
        List<Account> paramList = new ArrayList<Account>(){{
            add(new Account("006", "zhangsan", 28.9));
            add(new Account("007", "lisi", 68.0));
            add(new Account("008", "wangwu", 58.2));
        }};

        jdbcTemplate.batchUpdate(batchInsertSql,new AccountBatchPreparedStatementSetter(paramList));
    }

    class AccountBatchPreparedStatementSetter implements BatchPreparedStatementSetter{
        private final List<Account> accounts;

        public AccountBatchPreparedStatementSetter(List<Account> accounts) {
            this.accounts = accounts;
        }

        @Override
        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,accounts.get(i).getId());
                preparedStatement.setString(2,accounts.get(i).getName());
                preparedStatement.setDouble(3,accounts.get(i).getMoney());
        }

        @Override
        public int getBatchSize() {
            return accounts.size();
        }
    }
}
