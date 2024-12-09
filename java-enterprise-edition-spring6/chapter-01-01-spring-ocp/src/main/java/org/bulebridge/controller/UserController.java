package org.bulebridge.controller;

import org.bulebridge.domain.User;
import org.bulebridge.service.IUserService;
import org.bulebridge.service.UserServiceImpl;

public class UserController {
    private IUserService userService = new UserServiceImpl();

    public User findUserByUserId(String userId) {
        return userService.findUserByUserId(userId);
    }
}
