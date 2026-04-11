package org.bluebridge.test;

import org.bluebridge.domain.Account;
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
 * @author lingwh
 * @desc   测试SpringJdbcTemplate的批量操作
 * @date   2019/3/25 16:03
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class JdbcTemplateBatchOperateTest {

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 初始化数据
     */
    @Test
    public void init(){
        jdbcTemplate.update("insert into t_account values (?,?,?)", "001","张三",10);
        jdbcTemplate.update("insert into t_account values (?,?,?)", "002","李四",20);
        jdbcTemplate.update("insert into t_account values (?,?,?)", "003","张三",30);
        jdbcTemplate.update("insert into t_account values (?,?,?)", "004","王五",40);
        jdbcTemplate.update("insert into t_account values (?,?,?)", "005","赵六",50);
    }

    /**
     * 批量增加操作
     */
    @Test
    public void fun1(){
        String batchInsertSql1 = "insert into t_account values ('006','测试批量操作',10)";
        String batchInsertSql2 = "insert into t_account values ('007','测试批量操作',10)";
        jdbcTemplate.batchUpdate(batchInsertSql1,batchInsertSql2);
    }

    /**
     * 批量增加操作:使用匿名内部类完成数据封装
     */
    @Test
    public void fun2(){
        String batchInsertSql = "insert into t_account(id,name,money) values (?,?,?)";
        List<Account> accountList = new ArrayList<Account>(){{
            add(new Account("008", "赵八", 28.9));
            add(new Account("009", "钱九", 68.0));
            add(new Account("010", "孙十", 58.2));
        }};

        BatchPreparedStatementSetter bpss = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,accountList.get(i).getId());
                preparedStatement.setString(2,accountList.get(i).getName());
                preparedStatement.setDouble(3,accountList.get(i).getMoney());
            }
            @Override
            public int getBatchSize() {
                return accountList.size();
            }
        };
        jdbcTemplate.batchUpdate(batchInsertSql,bpss);
    }

    /**
     * 批量增加操作:不使用匿名内部类内部类完成数据封装
     */
    @Test
    public void fun3(){
        String batchInsertSql = "insert into t_account(id,name,money) values (?,?,?)";
        List<Account> paramList = new ArrayList<Account>(){{
            add(new Account("011", "周十一", 28.9));
            add(new Account("012", "吴十二", 68.0));
            add(new Account("013", "郑十三", 58.2));
        }};
        jdbcTemplate.batchUpdate(batchInsertSql,new AccountBatchPreparedStatementSetter(paramList));
    }

    class AccountBatchPreparedStatementSetter implements BatchPreparedStatementSetter{
        private final List<Account> accountList;

        public AccountBatchPreparedStatementSetter(List<Account> accountList) {
            this.accountList = accountList;
        }

        @Override
        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,accountList.get(i).getId());
                preparedStatement.setString(2,accountList.get(i).getName());
                preparedStatement.setDouble(3,accountList.get(i).getMoney());
        }

        @Override
        public int getBatchSize() {
            return accountList.size();
        }
    }
}
