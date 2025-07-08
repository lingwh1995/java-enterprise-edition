package org.bluebridge.ioc.basic.service;

import org.springframework.stereotype.Service;

/**
 * @author lingwh
 * @desc
 * @date   2019/3/17 9:42
 */
@Service("userService")
public class UserServiceImpl implements UserService{
    public void sayHello() {
        System.out.println("Hello World!");
    }
}
