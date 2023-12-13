package org.bluebridge.declarative.profile.service;

import org.bluebridge.declarative.profile.dao.AccountDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 使用PlatformTransactionManager 实现编程式事务
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountDao accountDao;

    @Override
    public void transfer(String transferInName, String transferOutName, double money) {
        accountDao.transferIn(transferOutName,money);
        //int i = 1 / 0;
        accountDao.transferOut(transferInName,money);
    }
}
