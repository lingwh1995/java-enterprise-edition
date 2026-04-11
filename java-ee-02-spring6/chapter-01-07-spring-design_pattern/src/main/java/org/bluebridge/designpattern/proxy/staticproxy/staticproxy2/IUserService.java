package org.bluebridge.designpattern.proxy.staticproxy.staticproxy2;

public interface IUserService {
    void addUser(User user);
    void deleteUserById(String id);
    void updateUser(User user);
    User getUserById(String id);
}
