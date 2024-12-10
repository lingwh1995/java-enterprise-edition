package org.bluebridge.propgation.service;

import org.bluebridge.propgation.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("accountTransferInService")
public class AccountTransferInServiceImpl implements IAccountTransferInService{

    @Autowired
    private AccountDao accountDao;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void transferIn(String transferInName, double money) {
        accountDao.transferIn(transferInName,money);
    }
}
