package org.bluebridge.propgation.service;

public interface IAccountTransferInService {

    /**
     * 给账户转入资金
     * @param transferInName
     * @param money
     */
    void transferIn(String transferInName,double money);
}
