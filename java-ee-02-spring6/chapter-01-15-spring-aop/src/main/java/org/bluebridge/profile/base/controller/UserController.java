package org.bluebridge.profile.base.controller;

import org.bluebridge.profile.base.domain.User;
import org.bluebridge.profile.base.service.IUserService;

public class UserController {

    private IUserService userService;

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    public void addUser(User user) {
        userService.addUser(user);
    }

    public int deleteUserById(String id) {
        return userService.deleteUserById(id);
    }

    public void updateUser(User user) {
        userService.updateUser(user);
    }

    public User getUserById(String id) {
        return userService.getUserById(id);
    }

    public void login() {
        userService.login();
    }
}
