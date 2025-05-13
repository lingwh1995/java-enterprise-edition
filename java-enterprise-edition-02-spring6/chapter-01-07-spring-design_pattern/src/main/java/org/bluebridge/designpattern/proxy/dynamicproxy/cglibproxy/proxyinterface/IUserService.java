package org.bluebridge.designpattern.proxy.dynamicproxy.cglibproxy.proxyinterface;


public interface IUserService {
    void deleteById(String id);
    User getById(String id);
    void showUsers();
}
