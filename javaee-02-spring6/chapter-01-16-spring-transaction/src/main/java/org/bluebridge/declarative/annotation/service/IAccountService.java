package org.bluebridge.declarative.annotation.service;

public interface IAccountService {

    void transfer(String transferInName, String transferOutName, double money);
}
