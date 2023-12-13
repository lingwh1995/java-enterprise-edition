package org.bluebridge.annotation.base.controller;


import org.bluebridge.annotation.base.domain.User;
import org.bluebridge.annotation.base.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

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
