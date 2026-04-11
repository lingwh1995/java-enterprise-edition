package org.bluebridge.propgation.service;

import org.bluebridge.propgation.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("accountTransferOutService")
public class AccountTransferOutServiceImpl implements IAccountTransferOutService{

    @Autowired
    private AccountDao accountDao;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void transferOut(String transferOutName, double money) {
        accountDao.transferOut(transferOutName,money);
        int i = 1/0;
    }
}
