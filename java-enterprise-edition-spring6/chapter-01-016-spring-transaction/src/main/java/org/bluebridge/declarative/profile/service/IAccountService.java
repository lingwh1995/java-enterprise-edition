package org.bluebridge.declarative.profile.service;

public interface IAccountService {

    void transfer(String transferInName, String transferOutName, double money);
}
