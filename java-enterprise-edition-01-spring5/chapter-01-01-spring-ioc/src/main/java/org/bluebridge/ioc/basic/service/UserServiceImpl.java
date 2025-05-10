package org.bluebridge.ioc.basic.service;

import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService{
    public void sayHello() {
        System.out.println("Hello World!");
    }
}
