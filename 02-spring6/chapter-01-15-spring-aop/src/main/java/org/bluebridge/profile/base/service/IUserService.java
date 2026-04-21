package org.bluebridge.profile.base.service;


import org.bluebridge.profile.base.domain.User;

public interface IUserService {
    void addUser(User user);
    int deleteUserById(String id);
    void updateUser(User user);
    User getUserById(String id);
    void login();
}
