package org.bluebridge.ioc.service;

import org.springframework.stereotype.Service;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/1 10:18
 */
@Service("userService")
public class UserServiceImpl implements UserService{
    public void sayHello() {
        System.out.println("Hello World!");
    }
}
