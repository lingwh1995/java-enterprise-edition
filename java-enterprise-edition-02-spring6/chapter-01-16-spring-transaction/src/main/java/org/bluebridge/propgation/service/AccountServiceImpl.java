package org.bluebridge.propgation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private IAccountTransferInService accountTransferInService;
    @Autowired
    private IAccountTransferOutService accountTransferOutService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void transfer(String transferInName, String transferOutName, double money) {
        accountTransferInService.transferIn(transferOutName,money);
        accountTransferOutService.transferOut(transferInName,money);
    }
}
