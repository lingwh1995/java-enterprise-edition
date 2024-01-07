package org.bulebridge;

import org.bulebridge.controller.UserController;
import org.bulebridge.domain.User;

public class Client {
    public static void main(String[] args) {
        UserController userController = new UserController();
        User user = userController.findUserByUserId("001");
        System.out.println(user);
    }
}
