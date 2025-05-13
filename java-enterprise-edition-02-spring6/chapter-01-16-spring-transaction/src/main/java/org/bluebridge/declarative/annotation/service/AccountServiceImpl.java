package org.bluebridge.declarative.annotation.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bluebridge.declarative.annotation.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 使用PlatformTransactionManager 实现编程式事务
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountDao accountDao;

    @Transactional
    @Override
    public void transfer(String transferInName, String transferOutName, double money) {
        accountDao.transferIn(transferOutName,money);
        //int i = 1 / 0;
        accountDao.transferOut(transferInName,money);
    }
}
