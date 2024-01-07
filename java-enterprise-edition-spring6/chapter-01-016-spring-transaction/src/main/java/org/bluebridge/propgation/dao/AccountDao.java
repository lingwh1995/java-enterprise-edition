package org.bluebridge.propgation.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao {

    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 给账户转入资金
     * @param transferInName
     * @param money
     */
    public void transferIn(String transferInName,double money) {
        logger.info("正在执行给收账人" + transferInName + "的账户转入" + money + "的操作");
        String sql = "update account set balance = balance + ? where accountHolderName = ?";
        jdbcTemplate.update(sql,money,transferInName);
    }

    /**
     * 从账户转出资金
     * @param transferOutName
     * @param money
     */
    public void transferOut(String transferOutName,double money) {
        logger.info("正在执行从转账人" + transferOutName + "的账户转出" + money + "的操作");
        String sql = "update account set balance = balance - ? where accountHolderName = ?";
        jdbcTemplate.update(sql,money,transferOutName);
    }
}
