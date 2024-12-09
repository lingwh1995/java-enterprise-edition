package org.bluebridge.annotation.autowired.bytype.controller;

import org.bluebridge.annotation.autowired.bytype.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    public void deleteById(String id) {
        userService.deleteById(id);
    }
}
