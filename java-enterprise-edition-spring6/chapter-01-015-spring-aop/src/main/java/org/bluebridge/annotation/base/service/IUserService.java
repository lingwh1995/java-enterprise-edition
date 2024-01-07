package org.bluebridge.annotation.base.service;

import org.bluebridge.annotation.base.domain.User;

public interface IUserService {
    void addUser(User user);
    int deleteUserById(String id);
    void updateUser(User user);
    User getUserById(String id);
    void login();
}
