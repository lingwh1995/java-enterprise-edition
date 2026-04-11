package org.bluebridge.programmatic.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bluebridge.programmatic.dao.AccountDao;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 使用PlatformTransactionManager 实现编程式事务
 */
public class AccountServiceImpl implements IAccountService {

    private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    private AccountDao accountDao;
    private PlatformTransactionManager transactionManager;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void transfer(String transferInName, String transferOutName, double money) {
        //1.定义默认的事务属性
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        //2.获取事务对象
        TransactionStatus transaction = transactionManager.getTransaction(defaultTransactionDefinition);
        try {
            accountDao.transferIn(transferOutName,money);
            //int i = 1 / 0;
            accountDao.transferOut(transferInName,money);
            //提交事务
            transactionManager.commit(transaction);
        }catch (Exception e) {
            logger.info("执行转账操作时发生了异常，异常信息是: " + e.getMessage());
            //回滚事务
            transactionManager.rollback(transaction);
        }
    }
}
