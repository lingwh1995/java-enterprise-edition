package org.bluebridge.propgation.service;

public interface IAccountTransferOutService {

    /**
     * 从账户转出资金
     * @param transferOutName
     * @param money
     */
    void transferOut(String transferOutName,double money);
}
