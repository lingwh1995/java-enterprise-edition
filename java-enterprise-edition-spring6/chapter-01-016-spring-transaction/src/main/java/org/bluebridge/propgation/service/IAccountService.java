package org.bluebridge.propgation.service;

public interface IAccountService {

    void transfer(String transferInName, String transferOutName, double money);
}
