package org.bluebridge.designpattern.proxy.dynamicproxy.jdkproxy;


public interface ICatService {
    void addCat(Cat user);
    void deleteCatById(String id);
    void updateCat(Cat user);
    Cat getCatById(String id);
}
