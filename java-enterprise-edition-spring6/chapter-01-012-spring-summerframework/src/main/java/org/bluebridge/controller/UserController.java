package org.bluebridge.controller;

import org.bluebridge.service.UserService;

public class UserController {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void deleteUserById(String id) {
        userService.deleteUserById(id);
    }
}
